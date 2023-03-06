package com.team.productservice.service.mapper.impl;

import com.team.productservice.data.Image;
import com.team.productservice.dto.ImageResponseDto;
import com.team.productservice.service.mapper.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageResponseMapper implements ObjectMapper<Image, ImageResponseDto> {
  @Override
  public ImageResponseDto map(Image from) {
    return new ImageResponseDto(from.getId(), from.getContent());
  }
}
