package com.team.jwtcommon.service;

import com.team.jwtcommon.properties.AccessTokenPropertiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(AccessTokenPropertiesExtractor.class)
@PropertySource("classpath:default-access-jwt.properties")
public class AccessJwtSecurityProviderTestConfig {
  @Bean
  AccessJwtSecurityProvider provider() {
    return new AccessJwtSecurityProvider();
  }
}
