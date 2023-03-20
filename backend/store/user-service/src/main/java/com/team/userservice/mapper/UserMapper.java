package com.team.userservice.mapper;

import com.team.userservice.data.Role;
import com.team.userservice.data.User;
import com.team.userservice.dto.RoleDto;
import com.team.userservice.dto.UserDto;
import com.team.userservice.startup.SetupUser;
import org.springframework.stereotype.Component;

import java.util.Locale;

public enum UserMapper {;
  public enum Request {;
    @Component
    public static class Common {
      public User toDomain(UserDto.Request.Common dto, String encodedPassword) {
        return new User(
          dto.getName(),
          dto.getEmail(),
          encodedPassword,
          dto.getActive(),
          Role.forValue("ROLE_" + dto.getRole().toValue().toUpperCase(Locale.ROOT))
        );
      }
    }
  }

  public enum Response {;
    @Component
    public static class Common {
      public UserDto.Response.Common toDto(User user) {
        return new UserDto.Response.Common(
          user.getId(),
          user.getName(),
          user.getEmail(),
          user.isActive(),
          RoleDto.forValue(user.getRole().getName().split("ROLE_")[1].toLowerCase())
        );
      }
    }
  }

  public enum Startup {;
    @Component
    public static class Common {
      public User toDomain(SetupUser setupUser, String encodedPassword) {
        return new User(
          setupUser.getName(),
          setupUser.getEmail(),
          encodedPassword,
          setupUser.isActive(),
          setupUser.getRole()
        );
      }
    }
  }
}
