package com.team.userservice.data;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum Role {
  USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

  @NotBlank
  private String name;

  Role(String name) {
    this.name = name;
  }

  public String toValue() {
    return name;
  }
}
