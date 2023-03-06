package com.team.userservice.mapper.impl;

import com.team.userservice.data.User;
import com.team.userservice.dto.UserResponseDto;
import com.team.userservice.mapper.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper implements ObjectMapper<User, UserResponseDto> {
  @Override
  public UserResponseDto map(User from) {
    return new UserResponseDto(
      from.getId(),
      from.getName(),
      from.getEmail(),
      from.isActive(),
      from.getRole()
    );
  }
}
