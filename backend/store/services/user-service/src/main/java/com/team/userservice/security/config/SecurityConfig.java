package com.team.userservice.security.config;

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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@ComponentScan("com.team.security")
public class SecurityConfig {
  private final AccessTokenFilter accessTokenFilter;
  private final SecurityConfigurer securityConfigurer;

  @Autowired
  public SecurityConfig(AuthenticationManager authenticationManager,
                        TokenMetadata accessTokenMetadata,
                        SecurityConfigurer securityConfigurer) {
    this.accessTokenFilter = new AccessTokenFilter(authenticationManager, accessTokenMetadata);
    this.securityConfigurer = securityConfigurer;
  }

  @Bean
  public SecurityFilterChain securityFilterChainUsers(HttpSecurity http) throws Exception {
    return securityConfigurer.createChain(http, accessTokenFilter, this::authorizeCustomizer);
  }

  private void authorizeCustomizer(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
    registry.requestMatchers("/api/users/activate").permitAll();
    registry.requestMatchers("/actuator/**").permitAll();
    registry.requestMatchers(POST, "/api/users").permitAll();
    registry.requestMatchers(GET, "/api/users").hasRole(Role.ADMIN.getName());
    registry.requestMatchers(GET, "/api/users/{id}").hasRole(Role.ADMIN.getName());
    registry.requestMatchers(PUT, "/api/users/{id}").hasRole(Role.ADMIN.getName());
    registry.requestMatchers(PATCH, "/api/users/email").hasAnyRole(Role.USER.getName(), Role.ADMIN.getName());
    registry.requestMatchers(DELETE, "/api/users/{id}").hasRole(Role.ADMIN.getName());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
