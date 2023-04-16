package com.team.jwtcommon.filter;

import com.team.basejwt.filter.AbstractTokenFilter;
import com.team.basejwt.properties.TokenMetadata;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

@Slf4j
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
