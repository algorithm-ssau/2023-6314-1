package com.team.orderservice.view.mapper;

import com.team.orderservice.model.Coordinate;
import com.team.orderservice.view.dto.CoordinateDto;
import org.springframework.stereotype.Component;

public enum CoordinateMapper {;
  public enum Request {;
    @Component
    public static final class Common {
      public Coordinate toDomain(CoordinateDto.Request.Common dto) {
        return new Coordinate(dto.getLatitude(), dto.getLongitude());
      }
    }
  }

  public enum Response {;
    @Component
    public static final class Common {
      public CoordinateDto.Response.Common toDto(Coordinate coordinate) {
        return new CoordinateDto.Response.Common(coordinate.getLatitude(), coordinate.getLongitude());
      }
    }
  }
}
