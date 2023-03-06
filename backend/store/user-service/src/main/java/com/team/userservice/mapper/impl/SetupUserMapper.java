package com.team.userservice.mapper.impl;

import com.team.userservice.data.User;
import com.team.userservice.mapper.ObjectMapper;
import com.team.userservice.startup.SetupUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetupUserMapper implements ObjectMapper<SetupUser, User> {
  private final PasswordEncoder passwordEncoder;

  @Override
  public User map(SetupUser from) {
    User user = new User();
    user.setName(from.getName());
    user.setEmail(from.getEmail());
    user.setActive(from.isActive());
    user.setRole(from.getRole());
    user.setPassword(passwordEncoder.encode(from.getPassword()));
    return user;
  }
}
