package com.team.jwtspringbootstarter.jwt.properties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

@Slf4j
public class TokenPropertiesExtractor {
  @Value("${jwt.token.access.header}")
  private String header;

  @Value("${jwt.token.access.secret}")
  private String accessSecret;

  @Value("${jwt.token.access.expired}")
  private long accessValidityInMilliseconds;

  @PostConstruct
  private void init() {
    accessSecret = Encoders.BASE64.encode(accessSecret.getBytes());
    log.debug("Encoded access and refresh secret key");
  }

  @lombok.Value
  public static class TokenData {
    String header;
    Key secretKey;
    long validityDateInMilliseconds;
  }

  public TokenData pullAccessTokenData() {
    return generateTokenData(accessSecret, accessValidityInMilliseconds);
  }

  private TokenData generateTokenData(String secret, long validityInMilliseconds) {
    return new TokenData(
      header,
      Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)),
      validityInMilliseconds
    );
  }
}
