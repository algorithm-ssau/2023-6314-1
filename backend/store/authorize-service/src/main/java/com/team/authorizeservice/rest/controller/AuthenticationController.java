package com.team.authorizeservice.rest.controller;

import com.team.authorizeservice.rest.dto.AuthenticationDto;
import com.team.authorizeservice.rest.dto.RequestMetadata;
import com.team.authorizeservice.security.details.ProjectionUserDetails;
import com.team.authorizeservice.service.api.RefreshSessionService;
import com.team.authorizeservice.service.api.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  private final TokenService tokenService;
  private final RefreshSessionService refreshSessionService;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthenticationController(
    TokenService tokenService,
    RefreshSessionService refreshSessionService,
    AuthenticationManager authenticationManager
  ) {
    this.tokenService = tokenService;
    this.refreshSessionService = refreshSessionService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(
    @Valid @RequestBody AuthenticationDto dto,
    HttpServletRequest request
  ) {
    var authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
    var authenticate = authenticationManager.authenticate(authToken);
    var user = (ProjectionUserDetails) authenticate.getPrincipal();
    var requestMetadata = new RequestMetadata(request);
    var tokenGroup = tokenService.generateTokenGroup(user, requestMetadata);
    return ResponseEntity.ok(Map.of("userId", user.getId(), "tokenGroup", tokenGroup));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(@CookieValue("refreshToken") String refreshToken) {
    refreshSessionService.deleteByToken(refreshToken);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(
    @CookieValue("refreshToken") String refreshToken,
    HttpServletRequest request
  ) {
    return ResponseEntity.ok(
      tokenService.refreshByToken(refreshToken, new RequestMetadata(request))
    );
  }

  //  @PostMapping("activate/{link}")

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handle(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
      .body(ex.getMessage());
  }
}
