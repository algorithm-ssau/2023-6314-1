package com.team.productservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponseDto {
  private Long id;
  private String name;
  private Long countInStock;
  private byte[] image;
}
