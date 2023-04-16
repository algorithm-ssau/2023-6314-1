package com.team.basejwt.service;

import com.team.basejwt.authentication.JwtAuthenticationToken;
import com.team.basejwt.properties.TokenMetadata;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
public abstract class JwtSecurityProvider implements AuthenticationProvider {
  protected final Claims extractTokenClaims(String token, TokenMetadata tokenMetadata) {
    return Jwts.parserBuilder()
      .setSigningKey(tokenMetadata.getSecretKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  @SuppressWarnings("unchecked")
  protected Collection<? extends GrantedAuthority> extractAuthorities(Claims claims) {
    var value = claims.get("authorities", Collection.class);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    for (Object o : value) {
      Collection<String> authorityNames = ((Map<String, String>) o).values();
      for (String authority : authorityNames) {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + authority));
      }
    }
    return authorities;
  }

  protected abstract boolean valid(String token, TokenMetadata tokenMetadata);

  protected Authentication loadAuthenticationByToken(String token, TokenMetadata tokenMetadata) {
    Claims claims = extractTokenClaims(token, tokenMetadata);
    Collection<? extends GrantedAuthority> authorities = extractAuthorities(claims);
    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token, tokenMetadata, authorities);
    authenticationToken.setAuthenticated(true);
    return authenticationToken;
  }

  @Override
  public Authentication authenticate(Authentication authentication) {
    JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;

    var token = (String) jwtAuthentication.getPrincipal();
    var tokenMetadata = (TokenMetadata) jwtAuthentication.getDetails();
    if (!valid(token, tokenMetadata)) {
      throw new JwtException("JWT token not valid or not present");
    }

    return loadAuthenticationByToken(token, tokenMetadata);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
