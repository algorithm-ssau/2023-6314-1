package com.team.productservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @Positive
  private Double cost;

  @Positive
  private Long countInStock;

  @Size(min = 1, max = 20)
  private List<ImageRequestDto> images;
}
