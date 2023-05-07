package com.team.basejwt.filter;

import com.team.basejwt.authentication.JwtAuthenticationToken;
import com.team.basejwt.properties.TokenMetadata;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public abstract class AbstractTokenFilter extends OncePerRequestFilter {
  private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
  protected final AuthenticationManager authenticationManager;
  protected final TokenMetadata tokenMetadata;

  public AbstractTokenFilter(AuthenticationManager authenticationManager,
                             TokenMetadata tokenMetadata) {
    this.authenticationManager = authenticationManager;
    this.tokenMetadata = tokenMetadata;
  }

  @Override
  public final void doFilterInternal(@NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException {
    try {
      Optional<String> tokenCandidate = resolveToken(request);
      if (tokenCandidate.isPresent()) {
        var authenticationToken = new JwtAuthenticationToken(tokenCandidate.get(), tokenMetadata);
        var authentication = authenticationManager.authenticate(authenticationToken);
        securityContextHolderStrategy.getContext().setAuthentication(authentication);
      }
    } catch (JwtException ex) {
      log.error(ex.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  protected abstract Optional<String> resolveToken(HttpServletRequest request);
}
