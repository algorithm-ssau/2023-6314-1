package com.team.productservice.security.сonfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

@Configuration
@ComponentScan("com.team.jwtcommon")
public class AuthenticationConfig {
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationProvider... providers) {
    return new ProviderManager(providers);
  }
}
