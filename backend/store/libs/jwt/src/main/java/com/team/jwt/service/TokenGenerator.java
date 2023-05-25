package com.team.jwt.service;

import com.team.jwt.properties.TokenMetadata;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Value;

import java.util.Date;

public class TokenGenerator {
  @Value
  private static class CommonInterval {
    @PastOrPresent Date created = new Date();
    @FutureOrPresent Date expired;

    public CommonInterval(Long expiredInMls) {
      this.expired = new Date(created.getTime() + expiredInMls);
    }
  }

  public String generateToken(Claims claims, TokenMetadata tokenMetadata) {
    var dateGroup = new CommonInterval(tokenMetadata.getValidityDateInMilliseconds());
    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(dateGroup.getCreated())
      .setExpiration(dateGroup.getExpired())
      .signWith(tokenMetadata.getSecretKey())
      .compact();
  }

  public String generateToken(String roleName, TokenMetadata tokenMetadata) {
    Claims claimsWithRole = createClaimsWithRole(roleName);
    return generateToken(claimsWithRole, tokenMetadata);
  }

  private Claims createClaimsWithRole(String roleName) {
    Claims claims = new DefaultClaims();
    claims.put("role", roleName);
    return claims;
  }
}
