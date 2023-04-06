package com.team.notificationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Value;

import java.time.OffsetDateTime;

@Data
@Value
public class ActivationDto {
  @NotBlank String name;
  @Email String email;
  @FutureOrPresent OffsetDateTime created;
  @NotBlank String activationLink;
}
