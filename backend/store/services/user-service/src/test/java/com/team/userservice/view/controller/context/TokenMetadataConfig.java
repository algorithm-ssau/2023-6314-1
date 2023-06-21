package com.team.userservice.view.controller.context;

import com.team.jwt.properties.TokenMetadata;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class TokenMetadataConfig {
  private static final String TEST_HEADER = "Test";
  private static final Key TEST_SECRET_KEY = Keys.hmacShaKeyFor("test".repeat(50).getBytes());
  private static final long TEST_VALIDITY = 10 * 1000 * 60;

  @Bean
  public TokenMetadata tokenMetadata() {
    return new TokenMetadata(TEST_HEADER, TEST_SECRET_KEY, TEST_VALIDITY);
  }
}
