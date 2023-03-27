package com.team.jwtspringbootstarter.jwt.filter;

import com.team.jwtspringbootstarter.jwt.authentication.JwtSecurityProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
public class AccessTokenFilter extends GenericFilterBean {
  private final JwtSecurityProvider jwtSecurityProvider;

  @Autowired
  public AccessTokenFilter(JwtSecurityProvider jwtSecurityProvider) {
    this.jwtSecurityProvider = jwtSecurityProvider;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    var token = jwtSecurityProvider.resolveToken((HttpServletRequest) request);
    log.debug("Resolved token: {}", token);

    if (token != null && jwtSecurityProvider.validateToken(token)) {
      Authentication authentication = jwtSecurityProvider.loadAuthenticationByToken(token);
      var securityContext = SecurityContextHolder.getContext();
      securityContext.setAuthentication(authentication);
      log.debug("Authenticated: {} in context: {}", authentication, securityContext);
    }
    chain.doFilter(request, response);
  }
}
