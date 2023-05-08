package com.team.userservice.startup;

import com.team.userservice.model.Role;
import lombok.Getter;

@Getter
public enum SetupUser {
  USER_1(
    "Lillie White",
    "lilliew@gmail.com",
    "12345678",
    Role.USER
  ),

  USER_2(
    "Edward Lopez",
    "edwl@gmail.com",
    "password",
    Role.USER
  ),

  USER_3(
    "Amanda Carter",
    "amandacarter@gmail.com",
    "uc9^*3kcx)^s!d73%^9Hc7hda",
    Role.USER
  ),

  ADMIN_1(
    "Harold Hernandez",
    "haroldhernandez@gmail.com",
    "#BEtEjwKwN8LmZr25m{1~I",
    Role.ADMIN
  );

  private final String name;
  private final String email;
  private final String password;
  private final boolean active = true;
  private final Role role;

  SetupUser(String name, String email, String password, Role role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
