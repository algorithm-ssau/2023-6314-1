package com.team.userservice.view.controller.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.userservice.model.Role;
import com.team.userservice.model.User;
import com.team.userservice.view.MapperFacade;
import com.team.userservice.view.dto.RoleDto;
import com.team.userservice.view.dto.UserDto;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

public class MapperFacadeMocker {
  private final MapperFacade mapperFacade;

  public MapperFacadeMocker(MapperFacade mapperFacade) {
    this.mapperFacade = mapperFacade;
  }

  public void mapperFacadeToCommonResponseDtoMock() {
    doAnswer(invoc -> {
      User arg = invoc.getArgument(0, User.class);
      return new UserDto.Response.Common(
        arg.getId(),
        arg.getName(),
        arg.getEmail(),
        arg.getActive(),
        RoleDto.forValue(arg.getRole().getName().substring(5).toLowerCase()),
        arg.getCreated(),
        arg.getUpdated()
      );
    }).when(mapperFacade)
      .toCommonResponseDto(any(User.class));
  }

  public void commonRequestToDomainMock() {
    doAnswer(invoc -> {
      UserDto.Request.Common arg = invoc.getArgument(0, UserDto.Request.Common.class);
      return User.builder()
        .email(arg.getEmail())
        .name(arg.getName())
        .password(arg.getPassword())
        .build();
    }).when(mapperFacade)
      .commonRequestToDomain(any(UserDto.Request.Common.class));
  }

  public void toActivationResponseDtoMock() {
    doAnswer(invoc -> {
      String urlRoot = "http://localhost:8080";
      User user = invoc.getArgument(0, User.class);
      return new UserDto.Response.Activation(
        user.getName(),
        user.getEmail(),
        urlRoot + "/test-link",
        OffsetDateTime.now()
      );
    }).when(mapperFacade)
      .toActivationResponseDto(any(User.class));
  }

  public void activationDtoToKafkaMessageMock(ObjectMapper objectMapper) {
    doAnswer(invoc -> {
      var dto = invoc.getArgument(0, UserDto.Response.Activation.class);
      return objectMapper.writeValueAsString(dto);
    }).when(mapperFacade)
      .activationDtoToKafkaMessage(any(UserDto.Response.Activation.class));
  }

  public void updateRequestToDomainMock() {
    doAnswer(invoc -> {
      var dto = invoc.getArgument(0, UserDto.Request.Update.class);
      return User.builder().name(dto.getName()).password(dto.getPassword()).role(Role.USER).build();
    }).when(mapperFacade)
      .updateRequestToDomain(any(UserDto.Request.Update.class));
  }
}
