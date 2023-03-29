package com.team.authorizeservice.security.config;

import com.team.authorizeservice.security.jwt.filter.RefreshTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeHttpRequests(matcherRegistry -> matcherRegistry.anyRequest().permitAll())
      .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
    var daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    return daoAuthenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationProvider... authenticationProviders) {
    return new ProviderManager(authenticationProviders);
  }

  @Bean
  public FilterRegistrationBean<RefreshTokenFilter> refreshTokenFilterFilterRegistrationBean(
    RefreshTokenFilter refreshTokenFilter
  ) {
    FilterRegistrationBean<RefreshTokenFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(refreshTokenFilter);
    registrationBean.addUrlPatterns("/api/auth/logout", "/api/auth/refresh");
    return registrationBean;
  }
}
