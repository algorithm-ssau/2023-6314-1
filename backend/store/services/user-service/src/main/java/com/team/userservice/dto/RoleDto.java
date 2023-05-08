package com.team.userservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.team.userservice.model.exception.RoleNotFoundException;

import java.util.Map;

public enum RoleDto {
  USER, ADMIN;

  private static final Map<String, RoleDto> jsonNamesMap = Map.of(
    "user", USER,
    "admin", ADMIN
  );

  @JsonCreator
  public static RoleDto forValue(String name) {
    RoleDto role = jsonNamesMap.get(name);
    if (role != null) {
      return role;
    }
    throw new RoleNotFoundException("Role type: " + name + " is not supported");
  }

  @JsonValue
  public String toValue() {
    for (Map.Entry<String, RoleDto> entry : jsonNamesMap.entrySet()) {
      if (entry.getValue() == this) {
        return entry.getKey();
      }
    }
    throw new RoleNotFoundException("Role type: " + this + " is not supported");
  }
}
