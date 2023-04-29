package com.team.identityprovider.security.jwt.properties;

import com.team.basejwt.properties.TokenMetadata;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenProperties {
  @Value("${jwt.token.access.header}")
  private String accessHeader;

  @Value("${jwt.token.access.secret}")
  private String accessSecret;

  @Value("${jwt.token.access.expired}")
  private long accessValidityInMilliseconds;

  @Value("${jwt.token.refresh.header}")
  private String refreshHeader;

  @Value("${jwt.token.refresh.secret}")
  private String refreshSecret;

  @Value("${jwt.token.refresh.expired}")
  private long refreshValidityInMilliseconds;

  @PostConstruct
  private void init() {
    refreshSecret = Encoders.BASE64.encode(refreshSecret.getBytes());
    accessSecret = Encoders.BASE64.encode(accessSecret.getBytes());
  }

  @Bean
  public TokenMetadata accessTokenMetadata() {
    return new TokenMetadata(
      accessHeader,
      Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret)),
      accessValidityInMilliseconds
    );
  }

  @Bean
  public TokenMetadata refreshTokenMetadata() {
    return new TokenMetadata(
      refreshHeader,
      Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret)),
      refreshValidityInMilliseconds
    );
  }
}
