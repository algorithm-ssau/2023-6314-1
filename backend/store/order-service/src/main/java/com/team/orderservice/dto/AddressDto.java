package com.team.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;

public enum AddressDto {;
  private interface Id { @PositiveOrZero Long getId(); }
  private interface Street { @NotBlank String getStreet(); }
  private interface City { @NotBlank String getCity(); }
  private interface Region { @NotBlank String getRegion(); }
  private interface Country { @NotBlank String getCountry(); }
  private interface Zipcode { @Pattern(regexp = "^\\d{5,6}(?:[-\\s]\\d{1,4})?$") String getZipcode(); }
  private interface CoordinateRequest { @NotNull CoordinateDto.Request.Common getCoordinate(); }
  private interface CoordinateResponse { @NotNull CoordinateDto.Response.Common getCoordinate(); }
  private interface Body extends Street, City, Region, Country, Zipcode {}

  public enum Request {;
    @Value
    public static class Common implements Body, CoordinateRequest {
      String street;
      String city;
      String region;
      String country;
      String zipcode;
      CoordinateDto.Request.Common coordinate;

      @JsonCreator
      public Common(
        @JsonProperty("street") String street,
        @JsonProperty("city") String city,
        @JsonProperty("region") String region,
        @JsonProperty("country") String country,
        @JsonProperty("zipcode") String zipcode,
        @JsonProperty("coordinate") CoordinateDto.Request.Common coordinate
      ) {
        this.street = street;
        this.city = city;
        this.region = region;
        this.country = country;
        this.zipcode = zipcode;
        this.coordinate = coordinate;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Id, Body, CoordinateResponse {
      Long id;
      String street;
      String city;
      String region;
      String country;
      String zipcode;
      CoordinateDto.Response.Common coordinate;
    }
  }
}
