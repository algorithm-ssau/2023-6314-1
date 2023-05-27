package com.team.jwt.config;

import com.team.jwt.service.AccessJwtSecurityProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@ComponentScan
@EnableWebSecurity
@PropertySource("classpath:default-access-jwt.properties")
public class AccessJwtConfig {
  @Bean
  public AccessJwtSecurityProvider jwtSecurityProvider() {
    return new AccessJwtSecurityProvider();
  }
}
