package com.team.userservice.view.controller.mock;

import com.team.userservice.service.impl.TokenProvider;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

public class TokenProviderMocker {
  private final TokenProvider tokenProvider;

  public TokenProviderMocker(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  public void isValidTokenMock() {
    doReturn(true).when(tokenProvider).isValidToken(anyString());
  }

  public void obtainEmailMock(String email) {
    doReturn(email)
      .when(tokenProvider)
      .obtainEmail(anyString());
  }
}
