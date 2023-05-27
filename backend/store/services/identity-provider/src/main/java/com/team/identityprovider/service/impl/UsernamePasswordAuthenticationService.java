package com.team.identityprovider.service.impl;

import com.team.identityprovider.security.details.ProjectionUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsernamePasswordAuthenticationService {
  private final AuthenticationManager authenticationManager;

  @Autowired
  public UsernamePasswordAuthenticationService(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public void authenticate(String email, String password) {
    var authToken = new UsernamePasswordAuthenticationToken(email, password);
    var authentication = authenticationManager.authenticate(authToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public ProjectionUserDetails obtainUserDetailsFromAuthentication() {
    var authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    return (ProjectionUserDetails) authentication.getPrincipal();
  }

  public Claims obtainClaimsFromAuthentication() {
    ProjectionUserDetails userDetails = obtainUserDetailsFromAuthentication();
    Claims claims = new DefaultClaims();
    claims.setSubject(userDetails.getEmail()).put("authorities", userDetails.getAuthorities());
    claims.put("id", userDetails.getId());
    return claims;
  }
}
