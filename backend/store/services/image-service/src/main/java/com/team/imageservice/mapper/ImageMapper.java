package com.team.imageservice.mapper;

import com.team.imageservice.data.Image;
import com.team.imageservice.dto.ImageDto;
import org.springframework.stereotype.Component;

public enum ImageMapper {;
  public enum Request {;
    @Component
    public static class Common {
      public Image toDomain(ImageDto.Request.Common dto) {
        return new Image(dto.getContent());
      }
    }
  }

  public enum Response {;
    @Component
    public static class Common {
      public ImageDto.Response.Common toDto(Image image) {
        return new ImageDto.Response.Common(image.getId(), image.getContent());
      }
    }
  }
}
