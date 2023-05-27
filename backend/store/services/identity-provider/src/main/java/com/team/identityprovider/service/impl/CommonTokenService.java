package com.team.identityprovider.service.impl;

import com.team.jwt.properties.TokenMetadata;
import com.team.identityprovider.service.contract.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class CommonTokenService implements TokenService {
  @Value
  private static class CommonInterval implements Interval {
    @PastOrPresent Date created = new Date();
    @FutureOrPresent Date expired;

    public CommonInterval(Long expiredInMls) {
      this.expired = new Date(created.getTime() + expiredInMls);
    }
  }

  @Override
  public String generateToken(Claims claims, TokenMetadata tokenMetadata) {
    var dateGroup = new CommonInterval(tokenMetadata.getValidityDateInMilliseconds());
    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(dateGroup.getCreated())
      .setExpiration(dateGroup.getExpired())
      .signWith(tokenMetadata.getSecretKey())
      .compact();
  }
}
