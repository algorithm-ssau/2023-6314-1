package com.team.userservice.view.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.jwt.authentication.JwtAuthenticationToken;
import com.team.jwt.service.JwtSecurityProvider;
import com.team.userservice.infrastructure.kafka.ActivationSender;
import com.team.userservice.model.Role;
import com.team.userservice.model.User;
import com.team.userservice.service.contract.UserService;
import com.team.userservice.service.impl.TokenProvider;
import com.team.userservice.view.MapperFacade;
import com.team.userservice.view.controller.context.UserControllerTestContextConfiguration;
import com.team.userservice.view.controller.data.TestDataGenerator;
import com.team.userservice.view.controller.mock.*;
import com.team.userservice.view.dto.RoleDto;
import com.team.userservice.view.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(UserControllerTestContextConfiguration.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false, addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @MockBean
  private UserService userService;

  @MockBean
  private ActivationSender activationSender;

  @MockBean
  private TokenProvider tokenProvider;

  @MockBean
  private MapperFacade mapperFacade;

  @MockBean
  private JwtSecurityProvider securityProvider;


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestDataGenerator testDataGenerator;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserServiceMocker userServiceMocker;

  @Autowired
  private MapperFacadeMocker mapperFacadeMocker;

  @Autowired
  private ActivationSenderMocker activationSenderMocker;

  @Autowired
  private TokenProviderMocker tokenProviderMocker;

  @Test
  void shouldCreateMockMvc() {
    assertNotNull(mockMvc);
  }

  @Test
  void shouldReturnAllUsers() throws Exception {
    var users = testDataGenerator.generateUserList();
    var expectedDtos = testDataGenerator.generateUserDtoResponseCommonList(users);

    userServiceMocker.findAllMock(users);
    mapperFacadeMocker.mapperFacadeToCommonResponseDtoMock();

    mockMvc.perform(get("/api/users"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(result -> {
        var resultJsonAsString = result.getResponse().getContentAsString();
        TypeReference<List<UserDto.Response.Common>> type = new TypeReference<>() {};

        List<UserDto.Response.Common> dtos = objectMapper.readValue(resultJsonAsString, type);
        assertEquals(20, dtos.size());
        assertIterableEquals(expectedDtos, dtos);
      });
  }

  @Test
  void shouldReturnUserById() throws Exception {
    var users = testDataGenerator.generateUserList();

    mapperFacadeMocker.mapperFacadeToCommonResponseDtoMock();
    userServiceMocker.findByIdMock(users);

    mockMvc.perform(get("/api/users/5"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(result -> {
        var contentAsString = result.getResponse().getContentAsString();
        var dto = objectMapper.readValue(contentAsString, UserDto.Response.Common.class);
        assertEquals(mapperFacade.toCommonResponseDto(users.get(5)), dto);
      });
  }

  @Test
  void shouldSaveToDatabaseAndSendKafkaMessage() throws Exception {
    var users = testDataGenerator.generateUserList();
    var broker = testDataGenerator.generateBroker();

    mapperFacadeMocker.commonRequestToDomainMock();
    mapperFacadeMocker.toActivationResponseDtoMock();
    mapperFacadeMocker.activationDtoToKafkaMessageMock(objectMapper);
    userServiceMocker.createMock(users);
    activationSenderMocker.sendActivationMock(broker);

    int usersCountBefore = users.size();
    int brokerTopicCountBefore = broker.get("t.activation.link").size();

    var input = new UserDto.Request.Common("test-name", "test@email.com", "test-password");
    mockMvc.perform(post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(input)))
      .andExpect(status().isOk());

    User user = mapperFacade.commonRequestToDomain(input);
    assertEquals(usersCountBefore + 1, users.size());
    assertEquals(user, users.get(users.size() - 1));

    assertEquals(brokerTopicCountBefore + 1, broker.get("t.activation.link").size());
    assertTrue(broker.containsKey("t.activation.link"));
  }

  @Test
  void shouldUpdateUser() throws Exception {
    List<User> userList = testDataGenerator.generateUserList();
    userServiceMocker.updateMock(userList);
    mapperFacadeMocker.updateRequestToDomainMock();

    int userListSizeBefore = userList.size();

    var requestDto = new UserDto.Request.Update("update-name", "update-password", RoleDto.ADMIN);
    mockMvc.perform(put("/api/users/0")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto)))
      .andExpect(status().isOk());

    assertEquals(userListSizeBefore, userList.size());
    assertEquals(mapperFacade.updateRequestToDomain(requestDto), userList.get(0));
  }

  @Test
  void shouldUpdateEmail() throws Exception {
    User saved = User.builder().id(0L).name("1").email("1@email.com").role(Role.USER).build();
    List<User> users = testDataGenerator.generateUserList();
    users.add(saved);

    Map<String, List<String>> broker = testDataGenerator.generateBroker();
    broker.put("t.activation.update", new ArrayList<>());

    userServiceMocker.updateMock(users);
    userServiceMocker.findByIdMock(users);
    mapperFacadeMocker.toActivationResponseDtoMock();
    mapperFacadeMocker.activationDtoToKafkaMessageMock(objectMapper);
    activationSenderMocker.sendActivationMock(broker);

    int brokerSizeBeforeUpdate = broker.get("t.activation.update").size();
    int userListBeforeUpdate = users.size();

    var input = new UserDto.Request.UpdateEmail("update@email.com");
    mockMvc.perform(patch("/api/users/email")
        .principal(mock(JwtAuthenticationToken.class))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(input)))
      .andExpect(status().isOk());

    int brokerSizeAfterUpdate = broker.get("t.activation.update").size();
    int userListAfterUpdate = users.size();

    assertEquals("update@email.com", users.get(0).getEmail());
    assertEquals(brokerSizeBeforeUpdate + 1, brokerSizeAfterUpdate);
    assertEquals(userListBeforeUpdate, userListAfterUpdate);
  }

  @Test
  void shouldDeleteUser() throws Exception {
    List<User> users = testDataGenerator.generateUserList();
    userServiceMocker.deleteByIdMock(users);
    User beforeDelete = users.get(0);
    int usersSizeBeforeDeleteUser = users.size();

    mockMvc.perform(delete("/api/users/0"))
      .andExpect(status().isOk());

    assertEquals(usersSizeBeforeDeleteUser - 1, users.size());
    assertNotEquals(beforeDelete, users.get(0));
  }


  @Test
  void shouldActivateUserByToken() throws Exception {
    List<User> users = testDataGenerator.generateUserList();
    tokenProviderMocker.isValidTokenMock();
    tokenProviderMocker.obtainEmailMock(users.get(0).getEmail());
    userServiceMocker.activateUserByEmailMock(users);

    mockMvc.perform(get("/api/users/activate")
        .param("activateToken", "activateToken"))
      .andExpect(status().isOk());

    assertTrue(users.get(0).getActive());
  }
}