package com.team.security.config;

import com.team.jwt.filter.AbstractTokenFilter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtFilterSecurityConfigurer implements SecurityConfigurer {

  @Override
  public SecurityFilterChain createChain(HttpSecurity http,
                                         AbstractTokenFilter tokenFilter,
                                         Customizer<AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry> customizer) throws Exception {
    return http
      .csrf().disable()
      .httpBasic().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeHttpRequests(reg -> {
        customizer.customize(reg);
        authorizeHttpRequestsCustomizer(reg);
      })
      .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  private void authorizeHttpRequestsCustomizer(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
    registry.requestMatchers("/v3/api-docs/**").permitAll();
    registry.requestMatchers("/swagger-ui/**").permitAll();
    registry.requestMatchers("/error").permitAll();
    registry.requestMatchers("/actuator/**").permitAll();
  }
}
