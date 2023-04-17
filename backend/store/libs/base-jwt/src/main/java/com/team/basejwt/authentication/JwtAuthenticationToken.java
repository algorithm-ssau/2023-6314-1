package com.team.basejwt.authentication;

import com.team.basejwt.properties.TokenMetadata;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthenticationToken implements Authentication {
  private final String token;
  private final TokenMetadata tokenMetadata;
  private final Collection<? extends GrantedAuthority> authorities;
  private boolean authenticated;

  public JwtAuthenticationToken(String token, TokenMetadata tokenMetadata, Collection<? extends GrantedAuthority> authorities) {
    this.token = token;
    this.tokenMetadata = tokenMetadata;
    this.authorities = authorities;
  }

  public JwtAuthenticationToken(String token, TokenMetadata tokenMetadata) {
    this.token = token;
    this.tokenMetadata = tokenMetadata;
    this.authorities = List.of();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public TokenMetadata getDetails() {
    return tokenMetadata;
  }

  @Override
  public String getPrincipal() {
    return token;
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return "JwtAuthenticationToken";
  }
}
