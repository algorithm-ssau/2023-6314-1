package com.team.jwt.service;

import com.team.jwt.properties.TokenMetadata;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class AccessJwtSecurityProvider extends JwtSecurityProvider {
  @Override
  protected boolean valid(String token, TokenMetadata tokenMetadata) {
    if (token == null) return false;
    Claims claims = extractTokenClaims(token, tokenMetadata);
    return !claims.getExpiration().before(new Date());
  }
}
