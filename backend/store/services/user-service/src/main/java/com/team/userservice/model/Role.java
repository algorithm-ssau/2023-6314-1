package com.team.userservice.model;

import com.team.userservice.model.exception.RoleNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum Role {
  USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

  @NotBlank
  private String name;

  Role(String name) {
    this.name = name;
  }

  public static Role forValue(String name) {
    for (Role role : Role.values()) {
      if (role.name.equals(name)) {
        return role;
      }
    }
    throw new RoleNotFoundException("Role with name: " + name + " not found");
  }
}
