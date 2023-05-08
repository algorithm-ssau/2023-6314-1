package com.team.identityprovider.security.config;

import com.team.identityprovider.infrastructure.repository.UserRepositoryAuthenticateProjection;
import com.team.identityprovider.security.details.ProjectionUserDetailsService;
import com.team.identityprovider.security.jwt.authentication.RefreshTokenSecurityProvider;
import com.team.identityprovider.service.contract.RefreshSessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationConfig {
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationProvider... providers) {
    return new ProviderManager(providers);
  }

  @Bean
  public RefreshTokenSecurityProvider refreshTokenSecurityProvider(UserDetailsService userDetailsService,
                                                                   RefreshSessionService refreshSessionService) {
    return new RefreshTokenSecurityProvider(userDetailsService, refreshSessionService);
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService service) {
    var daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(service);
    return daoAuthenticationProvider;
  }

  @Bean
  ProjectionUserDetailsService projectionUserDetailsService(UserRepositoryAuthenticateProjection repository) {
    return new ProjectionUserDetailsService(repository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
