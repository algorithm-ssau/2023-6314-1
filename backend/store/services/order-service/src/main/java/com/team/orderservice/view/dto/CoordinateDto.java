package com.team.orderservice.view.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

public enum CoordinateDto {;
  private interface Latitude {
    @Pattern(regexp = "^-?([0-8]?[0-9]|90)(\\.[0-9]{1,40})?$")
    String getLatitude();
  }
  private interface Longitude {
    @Pattern(regexp = "^-?([0-9]{1,2}|1[0-7][0-9]|180)(\\.[0-9]{1,40})?$")
    String getLongitude();
  }

  public enum Request {;
    @Value
    public static class Common implements Latitude, Longitude {
      String latitude;
      String longitude;

      @JsonCreator
      public Common(
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude
      ) {
        this.latitude = latitude;
        this.longitude = longitude;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Latitude, Longitude {
      String latitude;
      String longitude;

      @JsonCreator
      public Common(
        @JsonProperty("latitude") String latitude,
        @JsonProperty("longitude") String longitude
      ) {
        this.latitude = latitude;
        this.longitude = longitude;
      }
    }
  }
}
