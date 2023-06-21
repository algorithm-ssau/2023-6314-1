package com.team.userservice.view.controller.mock;

import com.team.userservice.service.impl.UrlMatcher;
import jakarta.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class UrlMatcherMocker {
  private final UrlMatcher urlMatcher;

  public UrlMatcherMocker(UrlMatcher urlMatcher) {
    this.urlMatcher = urlMatcher;
  }

  public void getUrlRootMock() {
    doReturn("test://test.com")
      .when(urlMatcher)
      .getUrlRoot(any(HttpServletRequest.class));
  }
}
