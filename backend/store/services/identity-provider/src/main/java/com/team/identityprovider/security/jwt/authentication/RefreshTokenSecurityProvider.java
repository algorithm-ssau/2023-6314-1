package com.team.identityprovider.security.jwt.authentication;

import com.team.jwt.properties.TokenMetadata;
import com.team.jwt.service.JwtSecurityProvider;
import com.team.identityprovider.service.contract.RefreshSessionService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

@Slf4j
public class RefreshTokenSecurityProvider extends JwtSecurityProvider {
  private final UserDetailsService userDetailsService;
  private final RefreshSessionService refreshSessionService;

  public RefreshTokenSecurityProvider(UserDetailsService userDetailsService,
                                      RefreshSessionService refreshSessionService) {
    this.userDetailsService = userDetailsService;
    this.refreshSessionService = refreshSessionService;
  }

  @Override
  protected boolean valid(String token, TokenMetadata tokenMetadata) {
    Claims claims = extractTokenClaims(token, tokenMetadata);
    return !isExpired(claims) && isPresentInDatabase(token);
  }

  private boolean isExpired(@NonNull Claims claims) {
    return claims.getExpiration().before(new Date());
  }

  private boolean isPresentInDatabase(String token) {
    return refreshSessionService.existsByToken(token);
  }

  @Override
  protected Authentication loadAuthenticationByToken(String token, TokenMetadata tokenMetadata) {
    var claims = extractTokenClaims(token, tokenMetadata);
    var username = claims.getSubject();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }
}
