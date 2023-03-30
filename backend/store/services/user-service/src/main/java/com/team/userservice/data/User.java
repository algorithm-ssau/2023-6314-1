package com.team.userservice.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "users_sequence", allocationSize = 10)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

  @Size(min = 6, max = 80)
  @Transient
  private String password;

  private boolean active = true;

  @Enumerated(EnumType.STRING)
  private Role role;

  public User(String name, String email, String password, boolean active, Role role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.active = active;
    this.role = role;
  }
}
