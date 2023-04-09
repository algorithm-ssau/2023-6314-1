package com.team.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class ActivationDto {
  @NotBlank String name;
  @Email String email;
  @FutureOrPresent OffsetDateTime created;
  @NotBlank String activationLink;

  @JsonCreator
  public ActivationDto(
    @JsonProperty("name") String name,
    @JsonProperty("email") String email,
    @JsonProperty("createdDateTime") OffsetDateTime created,
    @JsonProperty("activationLink") String activationLink
  ) {
    this.name = name;
    this.email = email;
    this.created = created;
    this.activationLink = activationLink;
  }
}
