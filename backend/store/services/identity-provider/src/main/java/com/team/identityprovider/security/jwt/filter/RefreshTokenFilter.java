package com.team.identityprovider.security.jwt.filter;

import com.team.identityprovider.view.resolve.HttpServletRequestResolver;
import com.team.jwt.filter.AbstractTokenFilter;
import com.team.jwt.properties.TokenMetadata;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

public class RefreshTokenFilter extends AbstractTokenFilter {
  private final HttpServletRequestResolver httpServletRequestResolver;

  public RefreshTokenFilter(AuthenticationManager authenticationManager,
                            TokenMetadata refreshTokenMetadata,
                            HttpServletRequestResolver httpServletRequestResolver) {
    super(authenticationManager, refreshTokenMetadata);
    this.httpServletRequestResolver = httpServletRequestResolver;
  }

  @Override
  protected Optional<String> resolveToken(HttpServletRequest request) {
    return httpServletRequestResolver.findTokenFromCookie(request, tokenMetadata);
  }
}