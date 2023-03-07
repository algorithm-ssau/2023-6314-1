package com.team.orderservice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinateResponseDto {

  @Pattern(regexp = "^-?([0-8]?[0-9]|90)(\\.[0-9]{1,40})?$")
  private String latitude;

  @Pattern(regexp = "^-?([0-9]{1,2}|1[0-7][0-9]|180)(\\.[0-9]{1,40})?$")
  private String longitude;

}
