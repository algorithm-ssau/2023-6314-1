package com.team.identityprovider.service.impl;

import com.team.identityprovider.service.contract.TokenService;
import com.team.jwt.properties.TokenMetadata;
import com.team.jwt.time.Interval;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommonTokenService implements TokenService {

  @Override
  public String generateToken(Claims claims, TokenMetadata tokenMetadata) {
    Interval interval = tokenMetadata.expiredInterval();
    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(interval.getCreated())
      .setExpiration(interval.getExpired())
      .signWith(tokenMetadata.getSecretKey())
      .compact();
  }
}
