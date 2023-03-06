package com.team.userservice.dto;

import com.team.userservice.data.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

  @Transient
  @Size(min = 5, max = 80)
  private String password;

  private boolean active = true;

  @Enumerated(EnumType.STRING)
  private Role role;
}
