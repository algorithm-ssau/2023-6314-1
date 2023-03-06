package com.team.userservice.dto;

import com.team.userservice.data.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

  private boolean active = true;

  @Enumerated(EnumType.STRING)
  private Role role;
}
