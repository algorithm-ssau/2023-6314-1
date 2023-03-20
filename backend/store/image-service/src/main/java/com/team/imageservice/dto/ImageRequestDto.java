package com.team.imageservice.dto;

import com.team.imageservice.data.Image;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequestDto {
  @NotNull
  private byte[] content;
}
