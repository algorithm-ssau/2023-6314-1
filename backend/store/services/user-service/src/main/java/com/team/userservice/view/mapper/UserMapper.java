package com.team.userservice.view.mapper;

import com.team.userservice.view.dto.RoleDto;
import com.team.userservice.view.dto.UserDto;
import com.team.userservice.model.User;
import com.team.userservice.infrastructure.seed.SetupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public enum UserMapper {;
  public enum Request {;
    @Component
    public static final class Common {
      private final RoleMapper roleMapper;

      @Autowired
      public Common(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
      }

      public User toDomain(UserDto.Request.Common dto, String encodedPassword) {
        return new User(
          dto.getName(),
          dto.getEmail(),
          encodedPassword,
          roleMapper.toDomain(RoleDto.USER)
        );
      }
    }

    @Component
    public static final class Update {
      private final RoleMapper roleMapper;

      @Autowired
      public Update(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
      }

      public User toDomain(UserDto.Request.Update dto, String encodedPassword) {
        return User.builder()
          .name(dto.getName())
          .password(encodedPassword)
          .role(roleMapper.toDomain(dto.getRole()))
          .build();
      }
    }
  }

  public enum Response {;
    @Component
    public static final class Common {
      private final RoleMapper roleMapper;

      @Autowired
      public Common(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
      }

      public UserDto.Response.Common toDto(User user) {
        return new UserDto.Response.Common(
          user.getId(),
          user.getName(),
          user.getEmail(),
          user.getActive(),
          roleMapper.toDto(user.getRole()),
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
