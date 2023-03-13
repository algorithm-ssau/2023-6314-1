package com.team.orderservice.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
  @NotNull
  private AddressRequestDto arrivalAddress;

  @ElementCollection
  private List<Long> products;

  @NotNull
  private Long userId;

  @PastOrPresent
  private OffsetDateTime payloadDateTime = OffsetDateTime.now();

  @FutureOrPresent
  private OffsetDateTime arrivalDateTime;
}
