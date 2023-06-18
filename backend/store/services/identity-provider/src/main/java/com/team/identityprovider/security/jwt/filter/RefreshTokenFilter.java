package com.team.identityprovider.security.jwt.filter;

import com.team.identityprovider.view.resolve.HttpServletResolver;
import com.team.jwt.filter.AbstractTokenFilter;
import com.team.jwt.properties.TokenMetadata;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

public class RefreshTokenFilter extends AbstractTokenFilter {
  private final HttpServletResolver httpServletResolver;

  public RefreshTokenFilter(AuthenticationManager authenticationManager,
                            TokenMetadata refreshTokenMetadata,
                            HttpServletResolver httpServletResolver) {
    super(authenticationManager, refreshTokenMetadata);
    this.httpServletResolver = httpServletResolver;
  }

  @Override
  protected Optional<String> resolveToken(HttpServletRequest request) {
    return httpServletResolver.findTokenFromCookie(request, tokenMetadata);
  }
}