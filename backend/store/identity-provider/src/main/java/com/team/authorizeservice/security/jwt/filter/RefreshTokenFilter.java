package com.team.authorizeservice.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.authorizeservice.security.jwt.authentication.JwtSecurityProvider;
import com.team.authorizeservice.security.jwt.exception.JwtAuthenticationException;
import com.team.authorizeservice.service.api.RefreshSessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class RefreshTokenFilter extends OncePerRequestFilter {
  private final JwtSecurityProvider jwtSecurityProvider;
  private final RefreshSessionService refreshSessionService;

  @Autowired
  public RefreshTokenFilter(JwtSecurityProvider jwtSecurityProvider, RefreshSessionService refreshSessionService) {
    this.jwtSecurityProvider = jwtSecurityProvider;
    this.refreshSessionService = refreshSessionService;
  }

  @Override
  public void doFilterInternal(HttpServletRequest request,
                               HttpServletResponse response,
                               FilterChain chain) throws IOException, ServletException {
    try {
      var resolvedToken = jwtSecurityProvider.resolveRefreshToken(request);
      var validToken = requiredValidToken(resolvedToken);
      log.debug("Resolved token: {}", validToken);
      chain.doFilter(request, response);
    } catch (JwtAuthenticationException ex) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write(new ObjectMapper().writeValueAsString(ex.getMessage()));
    }
  }

  private String requiredValidToken(String token) {
    var isValidAndExistsToken = token != null
      && jwtSecurityProvider.validateRefreshToken(token)
      && refreshSessionService.existsByToken(token);
    if (!isValidAndExistsToken) {
      throw new JwtAuthenticationException("Token is not valid or does not exist");
    }
    return token;
  }
}