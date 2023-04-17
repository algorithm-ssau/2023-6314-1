package com.team.orderservice.security.config;

import com.team.jwtspringbootstarter.jwt.authentication.JwtSecurityProvider;
import com.team.jwtspringbootstarter.jwt.filter.AccessTokenFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@ComponentScan("com.team.jwtspringbootstarter.jwt.config")
public class SecurityConfig {
  private final AccessTokenFilter accessTokenFilter;

  @Autowired
  public SecurityConfig(JwtSecurityProvider provider) {
    this.accessTokenFilter = new AccessTokenFilter(provider);
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
    registry.requestMatchers(HttpMethod.POST, "/error").permitAll();
    registry.requestMatchers(HttpMethod.GET, "/api/orders").hasRole("ADMIN");
    registry.requestMatchers(HttpMethod.POST, "/api/orders").hasRole("ADMIN");
    registry.requestMatchers(HttpMethod.GET, "/api/orders/mine").hasAnyRole("USER", "ADMIN");
    registry.requestMatchers(HttpMethod.DELETE, "/api/orders/mine").hasAnyRole("USER", "ADMIN");
    registry.requestMatchers(HttpMethod.GET, "/api/orders/summary").hasAnyRole("USER", "ADMIN");
    registry.requestMatchers(HttpMethod.GET, "/api/orders/{id}").hasRole("ADMIN");
    registry.requestMatchers(HttpMethod.DELETE, "/api/orders/{id}").hasRole("ADMIN");
  }
}
