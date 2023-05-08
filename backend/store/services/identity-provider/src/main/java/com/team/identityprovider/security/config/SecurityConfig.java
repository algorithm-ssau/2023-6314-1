package com.team.identityprovider.security.config;

import com.team.basejwt.properties.TokenMetadata;
import com.team.identityprovider.security.jwt.filter.RefreshTokenFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final RefreshTokenFilter refreshTokenFilter;

  public SecurityConfig(@Qualifier("refreshTokenMetadata") TokenMetadata tokenMetadata,
                        AuthenticationManager authenticationManager) {
    this.refreshTokenFilter = new RefreshTokenFilter(authenticationManager, tokenMetadata);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .cors().configurationSource(this::corsConfigurationSource).and()
      .csrf().disable()
      .httpBasic().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeHttpRequests(this::authorizeHttpRequestsCustomizer)
      .addFilterBefore(refreshTokenFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  private CorsConfiguration corsConfigurationSource(HttpServletRequest request) {
    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    corsConfiguration.addAllowedMethod(HttpMethod.PUT);
    corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
    return corsConfiguration;
  }

  private void authorizeHttpRequestsCustomizer(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
    registry.requestMatchers("/v3/api-docs/**").permitAll();
    registry.requestMatchers("/swagger-ui/**").permitAll();
    registry.requestMatchers("/error").permitAll();
    registry.requestMatchers(HttpMethod.POST, "/api/auth/trust-service-login").permitAll();
    registry.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
    registry.requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated();
    registry.requestMatchers(HttpMethod.POST, "/api/auth/refresh").authenticated();
    registry.requestMatchers("/error").permitAll();
  }
}
