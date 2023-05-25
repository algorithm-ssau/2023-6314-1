package com.team.security.config;

import com.team.jwt.filter.AbstractTokenFilter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

public interface SecurityConfigurer {
  SecurityFilterChain createChain(HttpSecurity http,
                                  AbstractTokenFilter tokenFilter,
                                  Customizer<AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry> customizer) throws Exception;
}
