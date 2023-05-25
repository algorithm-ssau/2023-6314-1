package com.team.productservice.security.сonfig;

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
    registry.requestMatchers(GET, "/api/products").permitAll();
    registry.requestMatchers(POST, "/api/products").hasRole(Role.ADMIN.getName());
    registry.requestMatchers(GET, "/api/products/{id}").permitAll();
    registry.requestMatchers(PUT, "/api/products/{id}").hasRole(Role.ADMIN.getName());
    registry.requestMatchers(DELETE, "/api/products/{id}").hasRole(Role.ADMIN.getName());
    registry.requestMatchers(GET, "/api/products/filter/{category-id}").permitAll();
    registry.requestMatchers(GET, "/api/categories/root").permitAll();
    registry.requestMatchers(GET, "/api/categories/{id}").permitAll();
    registry.requestMatchers(GET, "/api/categories/{id}/subs").permitAll();
  }
}
