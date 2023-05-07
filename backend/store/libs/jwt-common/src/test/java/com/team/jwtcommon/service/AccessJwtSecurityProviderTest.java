package com.team.jwtcommon.service;

import com.team.basejwt.properties.TokenMetadata;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = AccessJwtSecurityProviderTestConfig.class)
class AccessJwtSecurityProviderTest {
  @Value("${invalid.token}")
  private String invalidToken;

  @Autowired
  private AccessJwtSecurityProvider provider;

  @Autowired
  private TokenMetadata metadata;

  @Test
  void shouldThrowsExpiredException() {
    assertThrows(ExpiredJwtException.class, () -> provider.valid(invalidToken, metadata));
  }
}