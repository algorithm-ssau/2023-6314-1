package com.team.jwt.filter;

import com.team.jwt.authentication.JwtSecurityProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
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
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {
    var optionalToken = jwtSecurityProvider.resolveToken(request);
    if (optionalToken.isPresent()) {
      log.debug("Resolved optionalToken: {}", optionalToken);
      String token = optionalToken.get();
      if (jwtSecurityProvider.validateToken(token)) {
        Authentication authentication = jwtSecurityProvider.loadAuthenticationByToken(token);
        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        log.debug("Authenticated: {} in context: {}", authentication, securityContext);
      }
    }
    filterChain.doFilter(request, response);
  }
}
