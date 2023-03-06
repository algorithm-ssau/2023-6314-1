package com.team.userservice.mapper.impl;

import com.team.userservice.data.User;
import com.team.userservice.dto.UserRequestDto;
import com.team.userservice.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestMapper implements ObjectMapper<UserRequestDto, User> {
  private final PasswordEncoder passwordEncoder;

  @Override
  public User map(UserRequestDto from) {
    return new User(
      from.getName(),
      from.getEmail(),
      passwordEncoder.encode(from.getPassword()),
      from.isActive(),
      from.getRole()
    );
  }
}
