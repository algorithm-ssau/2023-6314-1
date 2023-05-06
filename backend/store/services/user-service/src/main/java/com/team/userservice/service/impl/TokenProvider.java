package com.team.userservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProvider {
  private final TokenConfig.TokenData activateTokenData;

  @Autowired
  public TokenProvider(@Qualifier("activateTokenData") TokenConfig.TokenData activateTokenData) {
    this.activateTokenData = activateTokenData;
  }

  public boolean isValidToken(String token) {
    try {
      return !extractTokenClaims(token).getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException ex) {
      throw new JwtException("JWT token is expired or invalid");
    }
  }

  public String obtainEmail(String token) {
    Claims claims = extractTokenClaims(token);
    return claims.getSubject();
  }

  private Claims extractTokenClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(activateTokenData.getSecretKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }
}
