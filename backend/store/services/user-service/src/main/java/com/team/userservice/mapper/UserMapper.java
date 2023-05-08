package com.team.userservice.mapper;

import com.team.userservice.model.Role;
import com.team.userservice.model.User;
import com.team.userservice.dto.RoleDto;
import com.team.userservice.dto.UserDto;
import com.team.userservice.startup.SetupUser;
import org.springframework.stereotype.Component;

import java.util.Locale;

public enum UserMapper {;
  public enum Request {;
    @Component
    public static final class Common {
      public User toDomain(UserDto.Request.Common dto, String encodedPassword) {
        return new User(
          dto.getName(),
          dto.getEmail(),
          encodedPassword,
          Role.forValue("ROLE_" + dto.getRole().toValue().toUpperCase(Locale.ROOT)),
          dto.getActive()
        );
      }
    }
  }

  public enum Response {;
    @Component
    public static final class Common {
      public UserDto.Response.Common toDto(User user) {
        return new UserDto.Response.Common(
          user.getId(),
          user.getName(),
          user.getEmail(),
          user.getActive(),
          RoleDto.forValue(user.getRole().getName().split("ROLE_")[1].toLowerCase()),
          user.getCreated(),
          user.getUpdated()
        );
      }
    }

    @Component
    public static final class Activation {
      public UserDto.Response.Activation toDto(User user, String activationLink) {
        return new UserDto.Response.Activation(
          user.getName(),
          user.getEmail(),
          activationLink,
          user.getCreated()
        );
      }
    }
  }

  public enum Startup {;
    @Component
    public static final class Common {
      public User toDomain(SetupUser setupUser, String encodedPassword) {
        return new User(
          setupUser.getName(),
          setupUser.getEmail(),
          encodedPassword,
          setupUser.getRole(),
          setupUser.isActive()
        );
      }
    }
  }
}
