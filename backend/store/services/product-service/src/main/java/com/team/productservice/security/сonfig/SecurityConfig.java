package com.team.productservice.security.сonfig;

import com.team.basejwt.properties.TokenMetadata;
import com.team.jwtcommon.filter.AccessTokenFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {
  private final AccessTokenFilter accessTokenFilter;

  @Autowired
  public SecurityConfig(AuthenticationManager authenticationManager, TokenMetadata tokenMetadata) {
    this.accessTokenFilter = new AccessTokenFilter(authenticationManager, tokenMetadata);
  }

  @Bean
  public SecurityFilterChain securityFilterChainUsers(HttpSecurity http) throws Exception {
    http
      .cors().configurationSource(this::corsConfigurationSource).and()
      .csrf().disable()
      .httpBasic().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeHttpRequests(this::authorizeHttpRequestsCustomizer)
      .addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
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
    registry.requestMatchers(HttpMethod.GET, "/api/products").permitAll();
    registry.requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN");
    registry.requestMatchers(HttpMethod.GET, "/api/products/{id}").permitAll();
    registry.requestMatchers(HttpMethod.PUT, "/api/products/{id}").hasRole("ADMIN");
    registry.requestMatchers(HttpMethod.DELETE, "/api/products/{id}").hasRole("ADMIN");
    registry.requestMatchers(HttpMethod.GET, "/api/products/filter/{category-id}").permitAll();
    registry.requestMatchers(HttpMethod.GET, "/api/categories/root").permitAll();
    registry.requestMatchers(HttpMethod.GET, "/api/categories/{id}").permitAll();
    registry.requestMatchers(HttpMethod.GET, "/api/categories/{id}/subs").permitAll();
  }
}
