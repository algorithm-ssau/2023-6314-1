package com.team.jwt.filter;

import com.team.jwt.properties.TokenMetadata;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

public class AccessTokenFilter extends AbstractTokenFilter {

  public AccessTokenFilter(AuthenticationManager authenticationManager, TokenMetadata accessTokenMetadata) {
    super(authenticationManager, accessTokenMetadata);
  }

  @Override
  protected Optional<String> resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(tokenMetadata.getHeader());
    String bearerPrefix = "Bearer ";

    return bearerToken != null && bearerToken.startsWith(bearerPrefix)
      ? Optional.of(bearerToken.substring(bearerPrefix.length()))
      : Optional.empty();
  }
}
