package com.team.jwtspringbootstarter.jwt.config;

import com.team.jwtspringbootstarter.jwt.authentication.JwtSecurityProvider;
import com.team.jwtspringbootstarter.jwt.filter.AccessTokenFilter;
import com.team.jwtspringbootstarter.jwt.properties.TokenPropertiesExtractor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class JwtSecurityConfig {
  @Bean
  public TokenPropertiesExtractor tokenPropertiesExtractor() {
    return new TokenPropertiesExtractor();
  }

  @Bean
  public JwtSecurityProvider jwtSecurityProvider() {
    return new JwtSecurityProvider(tokenPropertiesExtractor());
  }

  @Bean
  public AccessTokenFilter accessTokenFilter() {
    return new AccessTokenFilter(jwtSecurityProvider());
  }
}
