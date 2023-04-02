package com.team.jwtspringbootstarter.jwt.config;

import com.team.jwtspringbootstarter.jwt.authentication.JwtSecurityProvider;
import com.team.jwtspringbootstarter.jwt.properties.TokenPropertiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@PropertySource(value = {"classpath:jwt.properties"})
@EnableWebSecurity
public class JwtSecurityConfig {
  @Bean
  public TokenPropertiesExtractor tokenPropertiesExtractor() {
    return new TokenPropertiesExtractor();
  }

  @Bean
  public JwtSecurityProvider jwtSecurityProvider(TokenPropertiesExtractor tokenPropertiesExtractor) {
    return new JwtSecurityProvider(tokenPropertiesExtractor);
  }
}
