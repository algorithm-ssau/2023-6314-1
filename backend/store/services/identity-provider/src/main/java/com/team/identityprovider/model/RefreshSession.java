package com.team.identityprovider.model;

import jakarta.persistence.*;
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

  @NotBlank
  @Column(columnDefinition = "text")
  private String refreshToken;

  public RefreshSession(
    Long userId,
    String userIp,
    String userBrowserFingerPrint,
    String refreshToken
  ) {
    this.userId = userId;
    this.userIp = userIp;
    this.userBrowserFingerPrint = userBrowserFingerPrint;
    this.refreshToken = refreshToken;
  }
}
