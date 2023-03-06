package com.team.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {
  @NotBlank
  private String street;

  @NotBlank
  private String city;

  @NotBlank
  private String region;

  @NotBlank
  private String country;

  @Pattern(regexp = "^\\d{5,6}(?:[-\\s]\\d{1,4})?$")
  private String zipcode;

  @NotNull
  private CoordinateRequestDto coordinate;
}
