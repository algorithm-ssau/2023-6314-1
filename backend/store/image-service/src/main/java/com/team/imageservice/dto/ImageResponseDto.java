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
  private Long id;

  @NotNull
  private byte[] content;

  public static ImageResponseDto from(Image image) {
    return new ImageResponseDto(image.getId(), image.getContent());
  }
}
