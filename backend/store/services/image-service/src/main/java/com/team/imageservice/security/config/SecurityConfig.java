package com.team.imageservice.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@ComponentScan("com.team.jwtspringbootstarter.jwt.config")
public class SecurityConfig {
//  private final AccessTokenFilter accessTokenFilter;
//
//  public SecurityConfig(JwtSecurityProvider jwtSecurityProvider) {
//    this.accessTokenFilter = new AccessTokenFilter(jwtSecurityProvider);
//  }

//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//    return httpSecurity
//      .csrf().disable()
//      .cors().disable()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//      .and()
//      .authorizeHttpRequests(matcherRegistry -> matcherRegistry
//        .requestMatchers(HttpMethod.GET, "/api/images/{id}").permitAll()
//        .requestMatchers(HttpMethod.POST, "/api/images/").hasRole("ADMIN")
//        .anyRequest().authenticated())
//      .addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class)
//      .build();
//  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
      .cors().configurationSource(request -> {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        return corsConfiguration;
      }).and()
      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeHttpRequests().anyRequest().permitAll()
      .and()
      .build();
  }
}
