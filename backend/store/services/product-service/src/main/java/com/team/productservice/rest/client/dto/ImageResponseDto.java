package com.team.productservice.rest.client.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDto {
  private Long id;

  @NotNull
  private byte[] content;
}
