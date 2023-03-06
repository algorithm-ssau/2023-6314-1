package com.team.userservice.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.team.userservice.exception.RoleNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Getter
public enum Role {
  USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

  @NotBlank
  private String name;

  Role(String name) {
    this.name = name;
  }

  private static final Map<String, Role> jsonNamesMap = Map.of(
    "user", USER,
    "admin", ADMIN
  );

  @JsonCreator
  public static Role forValue(String name) {
    Role role = jsonNamesMap.get(name);
    if (role != null) {
      return role;
    }
    throw new RoleNotFoundException("Role type: " + name + " is not supported");
  }

  @JsonValue
  public String toValue() {
    for (Map.Entry<String, Role> entry : jsonNamesMap.entrySet()) {
      if (entry.getValue() == this) {
        return entry.getKey();
      }
    }
    throw new RoleNotFoundException("Role type: " + this + " is not supported");
  }
}
