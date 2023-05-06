package com.team.userservice.service.impl;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.security.Key;

@Configuration
@PropertySource("classpath:activate-token.properties")
public class TokenConfig {

  @Value("${jwt.token.activate.secret}")
  private String activateSecret;

  @Value("${jwt.token.activate.expired}")
  private long activateValidityInMilliseconds;

  @PostConstruct
  private void init() {
    activateSecret = Encoders.BASE64.encode(activateSecret.getBytes());
  }

  @lombok.Value
  public static class TokenData {
    Key secretKey;
    long validityDateInMilliseconds;
  }

  @Bean
  public TokenData activateTokenData() {
    return new TokenData(
      Keys.hmacShaKeyFor(Decoders.BASE64.decode(activateSecret)),
      activateValidityInMilliseconds
    );
  }
}
