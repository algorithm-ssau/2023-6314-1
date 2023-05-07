package com.team.identityprovider.security.jwt.filter;

import com.team.basejwt.filter.AbstractTokenFilter;
import com.team.basejwt.properties.TokenMetadata;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

public class RefreshTokenFilter extends AbstractTokenFilter {

  public RefreshTokenFilter(AuthenticationManager authenticationManager,
                            TokenMetadata refreshTokenMetadata) {
    super(authenticationManager, refreshTokenMetadata);
  }

  @Override
  protected Optional<String> resolveToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) return Optional.empty();

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(tokenMetadata.getHeader())) {
        return Optional.of(cookie.getValue());
      }
    }
    return Optional.empty();
  }
}