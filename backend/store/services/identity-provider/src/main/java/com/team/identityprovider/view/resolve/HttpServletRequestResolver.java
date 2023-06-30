package com.team.identityprovider.view.resolve;

import com.team.jwt.properties.TokenMetadata;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HttpServletRequestResolver {
  public Optional<String> findTokenFromCookie(HttpServletRequest request,
                                              TokenMetadata tokenMetadata) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) return Optional.empty();

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(tokenMetadata.getHeader())) {
        return Optional.of(cookie.getValue());
      }
    }
    return Optional.empty();
  }

  public String getTokenFromCookie(HttpServletRequest request,
                                   TokenMetadata tokenMetadata) {
    Optional<String> tokenFromCookie = findTokenFromCookie(request, tokenMetadata);
    if (tokenFromCookie.isEmpty()) {
      throw new IllegalArgumentException("Refresh token to found in cookies");
    }
    return tokenFromCookie.get();
  }
}
