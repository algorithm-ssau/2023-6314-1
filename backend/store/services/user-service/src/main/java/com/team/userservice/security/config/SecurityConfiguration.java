package com.team.userservice.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@ComponentScan("com.team.jwtspringbootstarter.jwt.config")
public class SecurityConfiguration {

  //Для авторизации
//  @Bean
//  @Primary
//  public SecurityFilterChain securityFilterChainUsers(HttpSecurity http) throws Exception {
//    http
//      .csrf().disable()
//      .cors().disable()
//      .httpBasic().disable()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//    .and()
//      .authorizeHttpRequests()
//      .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
//      .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//      .requestMatchers(HttpMethod.GET, "/api/users/").hasRole("ADMIN")
//      .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("ADMIN")
//      .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
//      .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN");
////    .and()
////      .addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class);
//    return http.build();
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

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
