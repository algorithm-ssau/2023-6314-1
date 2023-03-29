package com.team.authorizeservice.security.jwt.builder.api;

import com.team.authorizeservice.service.api.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Collection;

public interface TokenBuilder {
  Claims buildJwtClaims(String email, Collection<? extends GrantedAuthority> authorities);
  String buildTokenBody(Claims claims, Key secret, TokenService.DateGroup dateGroup);
}
