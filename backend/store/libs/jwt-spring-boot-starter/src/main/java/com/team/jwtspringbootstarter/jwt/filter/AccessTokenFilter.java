package com.team.jwtspringbootstarter.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.jwtspringbootstarter.jwt.authentication.JwtSecurityProvider;
import com.team.jwtspringbootstarter.jwt.exception.JwtAuthenticationException;
import com.team.jwtspringbootstarter.jwt.exception.JwtAuthorizeException;
import com.team.jwtspringbootstarter.jwt.exception.TokenResolvingException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class AccessTokenFilter extends OncePerRequestFilter {
  private final JwtSecurityProvider jwtSecurityProvider;

  public AccessTokenFilter(JwtSecurityProvider jwtSecurityProvider) {
    this.jwtSecurityProvider = jwtSecurityProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      var token = jwtSecurityProvider.resolveToken(request);
      log.debug("Resolved token: {}", token);

      if (token != null && jwtSecurityProvider.validateToken(token)) {
        Authentication authentication = jwtSecurityProvider.loadAuthenticationByToken(token);
        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        log.debug("Authenticated: {} in context: {}", authentication, securityContext);
      } else {
        throw new JwtAuthorizeException("Token not valid");
      }
      filterChain.doFilter(request, response);
    } catch (JwtAuthorizeException | TokenResolvingException ex) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write(new ObjectMapper().writeValueAsString(ex.getMessage()));
    }
  }
}
