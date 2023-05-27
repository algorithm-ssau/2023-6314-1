package com.team.userservice.service.impl;

import com.team.userservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TokenBuilder {
  private final TokenConfig.TokenData activateTokenData;

  @Autowired
  public TokenBuilder(@Qualifier("activateTokenData") TokenConfig.TokenData activateTokenData) {
    this.activateTokenData = activateTokenData;
  }

  public String buildTokenBody(User user) {
    Date currentDate = new Date();
    Date expiredDate = new Date(currentDate.getTime() + activateTokenData.getValidityDateInMilliseconds());

    return Jwts.builder()
      .setClaims(buildJwtClaims(user.getEmail()))
      .setIssuedAt(currentDate)
      .setExpiration(expiredDate)
      .signWith(activateTokenData.getSecretKey())
      .compact();
  }

  private Claims buildJwtClaims(String email) {
    var claims = Jwts.claims();
    claims.setSubject(email);
    return claims;
  }
}
