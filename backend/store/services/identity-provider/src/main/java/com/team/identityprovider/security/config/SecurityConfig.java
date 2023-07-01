package com.team.identityprovider.security.config;

import com.team.identityprovider.security.jwt.filter.RefreshTokenFilter;
import com.team.identityprovider.view.resolve.HttpServletRequestResolver;
import com.team.jwt.properties.TokenMetadata;
import com.team.security.config.SecurityConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@ComponentScan("com.team.security")
public class SecurityConfig {
  private final RefreshTokenFilter refreshTokenFilter;
  private final SecurityConfigurer securityConfigurer;

  public SecurityConfig(@Qualifier("refreshMetadata") TokenMetadata tokenMetadata,
                        AuthenticationManager authenticationManager,
                        SecurityConfigurer securityConfigurer,
                        HttpServletRequestResolver httpServletRequestResolver) {
    this.refreshTokenFilter = new RefreshTokenFilter(authenticationManager, tokenMetadata, httpServletRequestResolver);
    this.securityConfigurer = securityConfigurer;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return securityConfigurer.createChain(http, refreshTokenFilter, this::authorizeCustomizer);
  }

  private void authorizeCustomizer(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
    registry.requestMatchers(POST, "/api/auth/trust-service-login").permitAll();
    registry.requestMatchers(POST, "/api/auth/login").permitAll();
    registry.requestMatchers(POST, "/api/auth/logout").authenticated();
    registry.requestMatchers(POST, "/api/auth/refresh").authenticated();
  }
}
