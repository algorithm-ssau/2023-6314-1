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
  private interface Products { @NotNull List<ProductDto.Response.Common> getProducts(); }
  private interface ProductsIds { @NotNull List<Long> getProductsIds(); }
  private interface User { @NotNull UserDto.Response.Common getUser(); }
  private interface UserId { @NotNull Long getUserId(); }
  private interface PayloadDateTime { @PastOrPresent OffsetDateTime getPayloadDateTime(); }
  private interface ArrivalDateTime { @FutureOrPresent OffsetDateTime getArrivalDateTime(); }

  public enum Request {;
    @Value
    public static class Common implements
      AddressRequest, Products, PayloadDateTime, ArrivalDateTime, User
    {
      AddressDto.Request.Common address;
      List<ProductDto.Response.Common> products;
      OffsetDateTime payloadDateTime;
      OffsetDateTime arrivalDateTime;
      UserDto.Response.Common user;

      @JsonCreator
      public Common(
        @JsonProperty("address") AddressDto.Request.Common address,
        @JsonProperty("products") List<ProductDto.Response.Common> products,
        @JsonProperty("payloadDateTime") OffsetDateTime payloadDateTime,
        @JsonProperty("arrivalDateTime") OffsetDateTime arrivalDateTime,
        @JsonProperty("user") UserDto.Response.Common user
      ) {
        this.address = address;
        this.products = products;
        this.payloadDateTime = payloadDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.user = user;
      }
    }

    @Value
    public static class Create implements
      AddressRequest, ProductsIds, PayloadDateTime, ArrivalDateTime, UserId
    {
      AddressDto.Request.Common address;
      List<Long> productsIds;
      OffsetDateTime payloadDateTime;
      OffsetDateTime arrivalDateTime;
      Long userId;

      @JsonCreator
      public Create(
        @JsonProperty("address") AddressDto.Request.Common address,
        @JsonProperty("productsIds") List<Long> productsIds,
        @JsonProperty("payloadDateTime") OffsetDateTime payloadDateTime,
        @JsonProperty("arrivalDateTime") OffsetDateTime arrivalDateTime,
        @JsonProperty("userId") Long userId
      ) {
        this.address = address;
        this.productsIds = productsIds;
        this.payloadDateTime = payloadDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.userId = userId;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements
      Id, AddressResponse, User, Products, PayloadDateTime, ArrivalDateTime
    {
      Long id;
      AddressDto.Response.Common address;
      List<ProductDto.Response.Common> products;
      OffsetDateTime payloadDateTime;
      OffsetDateTime arrivalDateTime;
      UserDto.Response.Common user;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("address") AddressDto.Response.Common address,
        @JsonProperty("products") List<ProductDto.Response.Common> products,
        @JsonProperty("payloadDateTime") OffsetDateTime payloadDateTime,
        @JsonProperty("arrivalDateTime") OffsetDateTime arrivalDateTime,
        @JsonProperty("user") UserDto.Response.Common user
      ) {
        this.id = id;
        this.address = address;
        this.products = products;
        this.payloadDateTime = payloadDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.user = user;
      }
    }
  }
}
