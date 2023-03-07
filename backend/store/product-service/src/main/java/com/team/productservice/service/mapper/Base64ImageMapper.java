package com.team.productservice.service.mapper;

import com.team.productservice.data.Image;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class Base64ImageMapper implements ObjectMapper<Image, String>{
  @Override
  public String map(Image from) {
    byte[] imageBytes = from.getContent();
    byte[] encodedBytes = Base64.getEncoder().encode(imageBytes);
    return "data:image/jpeg;base64," + new String(encodedBytes);
  }
}
