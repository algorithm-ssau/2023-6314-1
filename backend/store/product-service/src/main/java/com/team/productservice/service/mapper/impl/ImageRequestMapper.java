package com.team.productservice.service.mapper.impl;

import com.team.productservice.data.Image;
import com.team.productservice.dto.ImageRequestDto;
import com.team.productservice.service.mapper.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageRequestMapper implements ObjectMapper<ImageRequestDto, Image> {
  @Override
  public Image map(ImageRequestDto from) {
    return new Image(from.getContent());
  }
}
