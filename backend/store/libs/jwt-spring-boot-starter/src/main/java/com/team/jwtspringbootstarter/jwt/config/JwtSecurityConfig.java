package com.team.jwtspringbootstarter.jwt.config;

import com.team.jwtspringbootstarter.jwt.authentication.JwtSecurityProvider;
import com.team.jwtspringbootstarter.jwt.filter.AccessTokenFilter;
import com.team.jwtspringbootstarter.jwt.properties.TokenPropertiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@PropertySource(value = {"classpath:jwt.properties"})
@EnableWebSecurity
public class JwtSecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeHttpRequests(matcherRegistry -> matcherRegistry.anyRequest().permitAll())
      .build();
  }

//  @Bean
//  public TokenPropertiesExtractor tokenPropertiesExtractor() {
//    return new TokenPropertiesExtractor();
//  }
//
//  @Bean
//  public JwtSecurityProvider jwtSecurityProvider(TokenPropertiesExtractor tokenPropertiesExtractor) {
//    return new JwtSecurityProvider(tokenPropertiesExtractor);
//  }
//
//  @Bean
//  public AccessTokenFilter accessTokenFilter(JwtSecurityProvider jwtSecurityProvider) {
//    return new AccessTokenFilter(jwtSecurityProvider);
//  }
}
