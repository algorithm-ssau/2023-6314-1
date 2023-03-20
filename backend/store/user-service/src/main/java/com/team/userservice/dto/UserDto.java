package com.team.userservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

public enum UserDto {;
  private interface Id { @NotNull Long getId(); }
  private interface Name { @NotBlank String getName(); }
  private interface EmailAddress { @Email String getEmail(); }
  private interface Password { @Size(min = 5, max = 80) String getPassword(); }
  private interface Active { @NotNull Boolean getActive(); }
  private interface Role { @NotNull RoleDto getRole(); }

  public enum Request {;
    @Value
    public static class Common implements Name, EmailAddress, Password, Active, Role {
      String name;
      String email;
      String password;
      Boolean active;
      RoleDto role;

      @JsonCreator
      public Common(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("active") Boolean active,
        @JsonProperty("role") RoleDto role
      ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = active;
        this.role = role;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Id, Name, EmailAddress, Active, Role {
      Long id;
      String name;
      String email;
      Boolean active;
      RoleDto role;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("active") Boolean active,
        @JsonProperty("role") RoleDto role
      ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
        this.role = role;
      }
    }
  }
}
