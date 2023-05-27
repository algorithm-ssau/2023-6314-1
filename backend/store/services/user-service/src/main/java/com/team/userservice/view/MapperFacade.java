package com.team.userservice.view;

import com.team.userservice.model.User;
import com.team.userservice.service.impl.TokenBuilder;
import com.team.userservice.view.dto.UserDto;
import com.team.userservice.view.mapper.KafkaMessageMapper;
import com.team.userservice.view.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MapperFacade {
  private final UserMapper.Request.Common commonRequestUserMapper;
  private final UserMapper.Request.Update updateRequestUserMapper;
  private final UserMapper.Response.Common commonResponseUserMapper;
  private final UserMapper.Response.Activation activationResponseUserMapper;
  private final KafkaMessageMapper kafkaMessageMapper;

  private final PasswordEncoder passwordEncoder;
  private final TokenBuilder tokenBuilder;

  public MapperFacade(UserMapper.Request.Common commonRequestUserMapper,
                      UserMapper.Request.Update updateRequestUserMapper,
                      UserMapper.Response.Common commonResponseUserMapper,
                      UserMapper.Response.Activation activationResponseUserMapper,
                      KafkaMessageMapper kafkaMessageMapper,
                      PasswordEncoder passwordEncoder,
                      TokenBuilder tokenBuilder) {
    this.commonRequestUserMapper = commonRequestUserMapper;
    this.updateRequestUserMapper = updateRequestUserMapper;
    this.commonResponseUserMapper = commonResponseUserMapper;
    this.activationResponseUserMapper = activationResponseUserMapper;
    this.kafkaMessageMapper = kafkaMessageMapper;
    this.passwordEncoder = passwordEncoder;
    this.tokenBuilder = tokenBuilder;
  }

  public User commonRequestToDomain(UserDto.Request.Common dto) {
    String encodedPassword = passwordEncoder.encode(dto.getPassword());
    return commonRequestUserMapper.toDomain(dto, encodedPassword);
  }

  public User updateRequestToDomain(UserDto.Request.Update dto) {
    String encodedPassword = passwordEncoder.encode(dto.getPassword());
    return updateRequestUserMapper.toDomain(dto, encodedPassword);
  }

  public UserDto.Response.Common toCommonResponseDto(User user) {
    return commonResponseUserMapper.toDto(user);
  }

  public UserDto.Response.Activation toActivationResponseDto(User user, String urlRoot) {
    String token = tokenBuilder.buildTokenBody(user);
    String activationLink = urlRoot + "/api/users/activate?activateToken=" + token;
    return activationResponseUserMapper.toDto(user, activationLink);
  }

  public String activationDtoToKafkaMessage(UserDto.Response.Activation dto) {
    return kafkaMessageMapper.toMessage(dto);
  }
}
