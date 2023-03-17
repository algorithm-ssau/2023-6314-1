package com.team.imageservice.dto;

import com.team.imageservice.data.Image;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDto {
  @NotNull
  private Long id;

  @NotNull
  private byte[] content;
}
