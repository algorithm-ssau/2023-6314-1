package com.team.orderservice.mapper;

import com.team.orderservice.model.Status;
import com.team.orderservice.dto.StatusDto;
import org.springframework.stereotype.Component;

public enum StatusMapper {;
  public enum Request {;
    @Component
    public static class Common {
      public Status toDomain(StatusDto.Request.Common from) {
        return Status.valueOf(from.getName());
      }
    }
  }

  public enum Response {;
    @Component
    public static class Common {
      public StatusDto.Response.Common toDto(Status from) {
        return new StatusDto.Response.Common(from.getName());
      }
    }
  }
}
