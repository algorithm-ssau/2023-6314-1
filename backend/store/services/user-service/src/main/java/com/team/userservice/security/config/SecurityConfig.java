package com.team.userservice.security.config;

import com.team.basejwt.properties.TokenMetadata;
import com.team.jwtcommon.filter.AccessTokenFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final AccessTokenFilter accessTokenFilter;

  @Autowired
  public SecurityConfig(AuthenticationManager authenticationManager,
                        TokenMetadata accessTokenMetadata) {
    this.accessTokenFilter = new AccessTokenFilter(authenticationManager, accessTokenMetadata);
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
    registry.requestMatchers("/api/users/activate").permitAll();
    registry.requestMatchers(HttpMethod.POST, "/api/users").permitAll();
    registry.requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN");
    registry.requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("ADMIN");
    registry.requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN");
    registry.requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
