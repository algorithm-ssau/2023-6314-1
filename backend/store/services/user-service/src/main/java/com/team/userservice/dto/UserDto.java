package com.team.userservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Value;

import java.time.OffsetDateTime;


public enum UserDto {;
  private interface Id { @NotNull Long getId(); }
  private interface Name { @NotBlank String getName(); }
  private interface EmailAddress { @Email String getEmail(); }
  private interface Password { @Size(min = 5, max = 80) String getPassword(); }
  private interface Active { Boolean getActive(); }
  private interface Role { @NotNull RoleDto getRole(); }
  private interface CreatedDateTime { @PastOrPresent OffsetDateTime getCreated(); }
  private interface UpdatedDateTime { @PastOrPresent OffsetDateTime getUpdated(); }
  private interface ActivationLink { @NotBlank String getActivationLink(); }

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
        @JsonProperty("role") RoleDto role
      ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = false;
        this.role = role;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Id, Name, EmailAddress, Active, Role, CreatedDateTime, UpdatedDateTime {
      Long id;
      String name;
      String email;
      Boolean active;
      RoleDto role;
      OffsetDateTime created;
      OffsetDateTime updated;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("active") Boolean active,
        @JsonProperty("role") RoleDto role,
        @JsonProperty("created") OffsetDateTime created,
        @JsonProperty("updated") OffsetDateTime updated
      ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
        this.role = role;
        this.created = created;
        this.updated = updated;
      }
    }

    @Value
    public static class Activation implements Name, EmailAddress, CreatedDateTime, ActivationLink {
      String name;
      String email;
      String activationLink;
      OffsetDateTime created;

      @JsonCreator
      public Activation(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("activationLink") String activationLink,
        @JsonProperty("created") OffsetDateTime created
      ) {
        this.name = name;
        this.activationLink = activationLink;
        this.email = email;
        this.created = created;
      }
    }
  }
}
