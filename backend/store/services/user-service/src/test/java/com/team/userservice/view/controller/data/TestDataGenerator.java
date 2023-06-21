package com.team.userservice.view.controller.data;

import com.team.userservice.model.Role;
import com.team.userservice.model.User;
import com.team.userservice.view.dto.RoleDto;
import com.team.userservice.view.dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class TestDataGenerator {
  public List<User> generateUserList() {
    return new ArrayList<>(Stream.generate(User::new)
      .limit(20)
      .peek(user -> user.setActive(false))
      .peek(user -> user.setId(ThreadLocalRandom.current().nextLong()))
      .peek(user -> user.setEmail(ThreadLocalRandom.current().nextInt() + "@email.com"))
      .peek(user -> user.setRole(Role.USER))
      .toList());
  }

  public List<UserDto.Response.Common> generateUserDtoResponseCommonList(List<User> users) {
    return new ArrayList<>(users.stream()
      .map(user -> new UserDto.Response.Common(
          user.getId(),
          user.getName(),
          user.getEmail(),
          user.getActive(),
          RoleDto.forValue(user.getRole().getName().substring(5).toLowerCase()),
          user.getCreated(),
          user.getUpdated()
        )
      ).toList());
  }

  public Map<String, List<String>> generateBroker() {
    return new HashMap<>(Map.of(
      "t.activation.link", new ArrayList<>(List.of("testmessage"))
    ));
  }
}
