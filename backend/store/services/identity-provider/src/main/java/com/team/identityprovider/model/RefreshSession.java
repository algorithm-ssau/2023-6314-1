package com.team.identityprovider.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class RefreshSession {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotBlank
  @Column(columnDefinition = "text")
  private String refreshToken;

  @NotNull
  private Long userId;

  @NotBlank
  private String userIp;

  @NotBlank
  private String userBrowserFingerPrint;

  @Column(name = "expired", columnDefinition = "timestamp")
  private Date expired;

  public RefreshSession(
    Long userId,
    String userIp,
    String userBrowserFingerPrint,
    String refreshToken,
    Date expired
  ) {
    this.userId = userId;
    this.userIp = userIp;
    this.userBrowserFingerPrint = userBrowserFingerPrint;
    this.refreshToken = refreshToken;
    this.expired = expired;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RefreshSession that = (RefreshSession) o;
    return Objects.equals(userId, that.userId) && Objects.equals(userIp, that.userIp) && Objects.equals(userBrowserFingerPrint, that.userBrowserFingerPrint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, userIp, userBrowserFingerPrint);
  }
}
