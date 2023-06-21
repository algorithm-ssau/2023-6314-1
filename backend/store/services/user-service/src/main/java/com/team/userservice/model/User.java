package com.team.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "created")
  private OffsetDateTime created = OffsetDateTime.now();

  @LastModifiedDate
  @Column(name = "updated")
  private OffsetDateTime updated = created;

  private Boolean active = false;

  public User(String name, String email, String password, Role role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public User(String name, String email, String password, Role role, boolean active) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.active = active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && role == user.role && Objects.equals(active, user.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, role, active);
  }
}
