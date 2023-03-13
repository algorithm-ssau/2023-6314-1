package com.team.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.team.orderservice.data.Status;
import com.team.orderservice.exception.StatusNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
public class StatusDto {
  private String statusName;

  public StatusDto(String statusName) {
    this.statusName = statusName;
  }

  @JsonCreator
  public static StatusDto forValue(String name) {
    boolean existStatus = Arrays.stream(Status.values())
      .anyMatch(status -> status.getName().equals(name));

    if (existStatus) {
      return new StatusDto(name);
    }
    throw new StatusNotFoundException("Status type: " + name + " is not supported");
  }

  @JsonValue
  public String toValue() {
    return statusName;
  }
}
