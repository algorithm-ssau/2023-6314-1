package com.team.authorizeservice.security.jwt.properties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@Slf4j
public class TokenPropertiesExtractor {
  @Value("${jwt.token.refresh.secret}")
  private String refreshSecret;

  @Value("${jwt.token.refresh.expired}")
  private long refreshValidityInMilliseconds;

  @PostConstruct
  private void init() {
    refreshSecret = Encoders.BASE64.encode(refreshSecret.getBytes());
    log.debug("Encoded access and refresh secret key");
  }

  @lombok.Value
  public static class TokenData {
    Key secretKey;
    long validityDateInMilliseconds;
  }

  public TokenData pullRefreshTokenData() {
    return generateTokenData(refreshSecret, refreshValidityInMilliseconds);
  }

  private TokenData generateTokenData(String secret, long validityInMilliseconds) {
    return new TokenData(
      Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)),
      validityInMilliseconds
    );
  }
}
