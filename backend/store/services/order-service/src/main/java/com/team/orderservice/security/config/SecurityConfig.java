package com.team.orderservice.security.config;

import com.team.jwt.filter.AccessTokenFilter;
import com.team.jwt.properties.TokenMetadata;
import com.team.security.config.Role;
import com.team.security.config.SecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;

@Configuration
@ComponentScan("com.team.security")
public class SecurityConfig {
  private final AccessTokenFilter accessTokenFilter;
  private final SecurityConfigurer securityConfigurer;

  @Autowired
  public SecurityConfig(AuthenticationManager authenticationManager,
                        TokenMetadata tokenMetadata,
                        SecurityConfigurer securityConfigurer) {
    this.accessTokenFilter = new AccessTokenFilter(authenticationManager, tokenMetadata);
    this.securityConfigurer = securityConfigurer;
  }

  @Bean
  public SecurityFilterChain securityFilterChainUsers(HttpSecurity http) throws Exception {
    return securityConfigurer.createChain(http, accessTokenFilter, this::authorizeCustomizer);
  }

  private void authorizeCustomizer(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
    registry.requestMatchers(GET, "/api/orders").hasRole(Role.ADMIN.getName());
    registry.requestMatchers(POST, "/api/orders").authenticated();
    registry.requestMatchers(GET, "/api/orders/mine").authenticated();
    registry.requestMatchers(DELETE, "/api/orders/mine").authenticated();
    registry.requestMatchers(GET, "/api/orders/summary").authenticated();
    registry.requestMatchers(GET, "/api/orders/{id}").hasRole(Role.ADMIN.getName());
    registry.requestMatchers(DELETE, "/api/orders/{id}").hasRole(Role.ADMIN.getName());
  }
}
