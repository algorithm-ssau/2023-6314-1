package com.team.orderservice.data;

import lombok.Getter;

@Getter
public enum Status {
  CREATED("created"),
  PAYED("payed"),
  TRANSFERRED("transferred"),
  CANCELED("canceled");

  private final String name;

  Status(String name) {
    this.name = name;
  }
}
