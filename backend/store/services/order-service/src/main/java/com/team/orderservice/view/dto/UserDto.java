package com.team.orderservice.view.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Value;

import java.time.OffsetDateTime;

public enum UserDto {;
  private interface Id { @NotNull Long getId(); }
  private interface Name { @NotBlank String getName(); }
  private interface EmailAddress { @Email String getEmail(); }
  private interface Active { Boolean getActive(); }
  private interface Role { @NotNull String getRole(); }
  private interface CreatedDateTime { @PastOrPresent OffsetDateTime getCreated(); }
  private interface UpdatedDateTime { @PastOrPresent OffsetDateTime getUpdated(); }

  public enum Response {;
    @Value
    public static class Common implements Id, Name, EmailAddress, Active, Role, CreatedDateTime, UpdatedDateTime {
      Long id;
      String name;
      String email;
      Boolean active;
      String role;
      OffsetDateTime created;
      OffsetDateTime updated;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("active") Boolean active,
        @JsonProperty("role") String role,
        @JsonProperty("createdDateTime") OffsetDateTime created,
        @JsonProperty("updatedDateTime") OffsetDateTime updated
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
  }
}
