package com.team.jwt.properties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

@Slf4j
public class TokenPropertiesExtractor {
  @Value("${jwt.token.access.header:${jwt.token.access.header.default}}")
  private String header;

  @Value("${jwt.token.access.secret:${jwt.token.access.secret.default}}")
  private String accessSecret;

  @Value("${jwt.token.access.expired:${jwt.token.access.expired.default}}")
  private long accessValidityInMilliseconds;

  @PostConstruct
  private void init() {
    accessSecret = Encoders.BASE64.encode(accessSecret.getBytes());
    log.debug("Encoded access secret key");
  }

  @lombok.Value
  public static class TokenData {
    String header;
    Key secretKey;
    long validityDateInMilliseconds;
  }

  public TokenData pullAccessTokenData() {
    return new TokenData(
      header,
      Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret)),
      accessValidityInMilliseconds
    );
  }
}
