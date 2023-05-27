package com.team.orderservice.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinate {

  @Pattern(regexp = "^-?([0-8]?[0-9]|90)(\\.[0-9]{1,40})?$")
  private String latitude;

  @Pattern(regexp = "^-?([0-9]{1,2}|1[0-7][0-9]|180)(\\.[0-9]{1,40})?$")
  private String longitude;
}
