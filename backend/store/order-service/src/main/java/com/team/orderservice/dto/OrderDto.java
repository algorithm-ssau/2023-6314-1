package com.team.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

public enum OrderDto {;
  private interface Id { @PositiveOrZero Long getId(); }
  private interface AddressRequest { @NotNull AddressDto.Request.Common getAddress(); }
  private interface AddressResponse { @NotNull AddressDto.Response.Common getAddress(); }
  private interface Products { @NotNull List<Long> getProducts(); }
  private interface User { @NotNull Long getUserId(); }
  private interface PayloadDateTime { @PastOrPresent OffsetDateTime getPayloadDateTime(); }
  private interface ArrivalDateTime { @FutureOrPresent OffsetDateTime getArrivalDateTime(); }
  private interface StatusRequest { @NotNull StatusDto.Request.Common getStatus(); }
  private interface StatusResponse { @NotNull StatusDto.Response.Common getStatus(); }
  private interface Base extends Products, PayloadDateTime, ArrivalDateTime {}


  public enum Request {;
    @Value
    public static class Common implements AddressRequest, StatusRequest, Base, User {
      AddressDto.Request.Common address;
      StatusDto.Request.Common status;
      List<Long> products;
      OffsetDateTime payloadDateTime;
      OffsetDateTime arrivalDateTime;
      Long userId;

      @JsonCreator
      public Common(
        @JsonProperty("address") AddressDto.Request.Common address,
        @JsonProperty("status") StatusDto.Request.Common status,
        @JsonProperty("products") List<Long> products,
        @JsonProperty("payloadDateTime") OffsetDateTime payloadDateTime,
        @JsonProperty("arrivalDateTime") OffsetDateTime arrivalDateTime,
        @JsonProperty("userId") Long userId
      ) {
        this.address = address;
        this.status = status;
        this.products = products;
        this.payloadDateTime = payloadDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.userId = userId;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Id, AddressResponse, StatusResponse, Base {
      Long id;
      AddressDto.Response.Common address;
      StatusDto.Response.Common status;
      List<Long> products;
      OffsetDateTime payloadDateTime;
      OffsetDateTime arrivalDateTime;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("address") AddressDto.Response.Common address,
        @JsonProperty("status") StatusDto.Response.Common status,
        @JsonProperty("products") List<Long> products,
        @JsonProperty("payloadDateTime") OffsetDateTime payloadDateTime,
        @JsonProperty("arrivalDateTime") OffsetDateTime arrivalDateTime
      ) {
        this.id = id;
        this.address = address;
        this.status = status;
        this.products = products;
        this.payloadDateTime = payloadDateTime;
        this.arrivalDateTime = arrivalDateTime;
      }
    }
  }
}
