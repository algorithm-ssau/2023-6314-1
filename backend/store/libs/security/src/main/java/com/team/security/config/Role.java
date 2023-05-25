package com.team.security.config;

public enum Role {
  USER("USER"), ADMIN("ADMIN"), SERVICE("SERVICE");

  private final String name;

  Role(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
