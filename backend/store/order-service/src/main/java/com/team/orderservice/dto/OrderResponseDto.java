package com.team.orderservice.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
  @PositiveOrZero
  private Long id;

  @NotNull
  private AddressResponseDto arrivalAddress;

  @ElementCollection
  private List<Long> products;

  @PastOrPresent
  private OffsetDateTime payloadDateTime = OffsetDateTime.now();

  @FutureOrPresent
  private OffsetDateTime arrivalDateTime;

}
