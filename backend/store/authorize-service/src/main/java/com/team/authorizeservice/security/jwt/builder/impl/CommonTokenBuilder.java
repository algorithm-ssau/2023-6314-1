package com.team.authorizeservice.security.jwt.builder.impl;

import com.team.authorizeservice.security.jwt.builder.api.TokenBuilder;
import com.team.authorizeservice.service.api.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;

@Component
public class CommonTokenBuilder implements TokenBuilder {
  public Claims buildJwtClaims(String email, Collection<? extends GrantedAuthority> authorities) {
    var claims = Jwts.claims();
    claims.setSubject(email);
    claims.put("authorities", authorities);
    return claims;
  }

  public String buildTokenBody(Claims claims, Key secret, TokenService.DateGroup dateGroup) {
    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(dateGroup.getCreated())
      .setExpiration(dateGroup.getExpired())
      .signWith(secret)
      .compact();
  }

}
