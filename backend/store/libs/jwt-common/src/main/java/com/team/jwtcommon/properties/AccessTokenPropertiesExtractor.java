package com.team.jwtcommon.properties;

import com.team.basejwt.properties.TokenMetadata;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessTokenPropertiesExtractor {
  @Value("${jwt.token.access.header.default}")
  private String header;

  @Value("${jwt.token.access.secret.default}")
  private String accessSecret;

  @Value("${jwt.token.access.expired.default}")
  private long accessValidityInMilliseconds;

  @PostConstruct
  private void init() {
    accessSecret = Encoders.BASE64.encode(accessSecret.getBytes());
  }

  @Bean
  public TokenMetadata accessTokenMetadata() {
    return new TokenMetadata(
      header,
      Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret)),
      accessValidityInMilliseconds
    );
  }
}
