package com.team.identityprovider.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class RefreshSession {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotNull
  private Long userId;

  @NotBlank
  private String userIp;

  @NotBlank
  private String userBrowserFingerPrint;

  @Column(name = "created", columnDefinition = "timestamp")
  private Date created = new Date();

  @FutureOrPresent
  @Column(name = "expired", columnDefinition = "timestamp")
  private Date expired;

  @NotBlank
  private String refreshToken;

  public RefreshSession(
    Long userId,
    String userIp,
    String userBrowserFingerPrint,
    Date expired,
    String refreshToken
  ) {
    this.userId = userId;
    this.userIp = userIp;
    this.userBrowserFingerPrint = userBrowserFingerPrint;
    this.expired = expired;
    this.refreshToken = refreshToken;
  }
}
