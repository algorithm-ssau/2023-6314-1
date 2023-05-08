package com.team.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team.orderservice.model.Status;
import com.team.orderservice.model.exception.StatusNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.Arrays;

public enum StatusDto {;
  private interface Name { @NotNull String getName(); }

  public enum Request {;
    @Value
    public static class Common implements Name {
      String name;

      @JsonCreator
      public Common(@JsonProperty("name") String name) {
        boolean existStatus = Arrays.stream(Status.values())
          .anyMatch(status -> status.getName().equals(name));
        if (!existStatus) {
          throw new StatusNotFoundException("Status type: " + name + " is not supported");
        }

        this.name = name;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Name {
      String name;

      @JsonCreator
      public Common(@JsonProperty("name") String name) {
        boolean existStatus = Arrays.stream(Status.values())
          .anyMatch(status -> status.getName().equals(name));
        if (!existStatus) {
          throw new StatusNotFoundException("Status type: " + name + " is not supported");
        }

        this.name = name;
      }
    }
  }
}
