package com.team.identityprovider.rest.controller;

import com.team.identityprovider.rest.dto.AuthenticationDto;
import com.team.identityprovider.rest.dto.RequestMetadata;
import com.team.identityprovider.service.api.AuthenticationService;
import com.team.identityprovider.service.api.RefreshSessionService;
import com.team.identityprovider.service.api.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  private final TokenService tokenService;
  private final RefreshSessionService refreshSessionService;
  private final AuthenticationService authenticationService;

  @Autowired
  public AuthenticationController(TokenService tokenService,
                                  RefreshSessionService refreshSessionService,
                                  AuthenticationService authenticationService) {
    this.tokenService = tokenService;
    this.refreshSessionService = refreshSessionService;
    this.authenticationService = authenticationService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDto dto,
                                 HttpServletRequest request) {
    var authenticatedUser = authenticationService.authenticate(dto.getEmail(), dto.getPassword());
    var tokenGroup = tokenService.generateTokenGroup(authenticatedUser, new RequestMetadata(request));
    return ResponseEntity.ok(Map.of("userId", authenticatedUser.getId(), "tokenGroup", tokenGroup));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(@CookieValue("refreshToken") String refreshToken) {
    refreshSessionService.deleteByToken(refreshToken);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(@CookieValue("refreshToken") String refreshToken,
                                   HttpServletRequest request) {
    var refreshedTokenGroup = tokenService.refreshByToken(refreshToken, new RequestMetadata(request));
    return ResponseEntity.ok(refreshedTokenGroup);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handle(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(ex.getMessage());
  }
}
