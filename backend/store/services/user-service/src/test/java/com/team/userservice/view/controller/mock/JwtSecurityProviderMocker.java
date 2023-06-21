package com.team.userservice.view.controller.mock;

import com.team.jwt.properties.TokenMetadata;
import com.team.jwt.service.JwtSecurityProvider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

public class JwtSecurityProviderMocker {
  private final JwtSecurityProvider securityProvider;

  public JwtSecurityProviderMocker(JwtSecurityProvider securityProvider) {
    this.securityProvider = securityProvider;
  }

  public void parseUserIdMock() {
    doReturn(0L)
      .when(securityProvider)
      .parseUserId(anyString(), any(TokenMetadata.class));
  }
}
