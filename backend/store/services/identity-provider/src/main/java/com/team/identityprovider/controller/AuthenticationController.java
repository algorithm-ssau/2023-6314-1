package com.team.identityprovider.controller;

import com.team.basejwt.properties.TokenMetadata;
import com.team.identityprovider.model.RefreshSession;
import com.team.identityprovider.dto.AuthenticationDto;
import com.team.identityprovider.dto.RequestMetadata;
import com.team.identityprovider.security.details.ProjectionUserDetails;
import com.team.identityprovider.service.impl.UsernamePasswordAuthenticationService;
import io.jsonwebtoken.Claims;
import com.team.identityprovider.service.contract.RefreshSessionService;
import com.team.identityprovider.service.contract.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  private final TokenService tokenService;
  private final RefreshSessionService refreshSessionService;
  private final UsernamePasswordAuthenticationService authenticationService;
  private final TokenMetadata accessTokenMetadata;
  private final TokenMetadata refreshTokenMetadata;

  @Autowired
  public AuthenticationController(TokenService tokenService,
                                  RefreshSessionService refreshSessionService,
                                  UsernamePasswordAuthenticationService authenticationService,
                                  TokenMetadata accessTokenMetadata,
                                  TokenMetadata refreshTokenMetadata) {
    this.tokenService = tokenService;
    this.refreshSessionService = refreshSessionService;
    this.authenticationService = authenticationService;
    this.accessTokenMetadata = accessTokenMetadata;
    this.refreshTokenMetadata = refreshTokenMetadata;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDto.Request.Common dto,
                                 HttpServletRequest request) {
    authenticationService.authenticate(dto.getEmail(), dto.getPassword());
    return generateTokenResponse(request);
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(HttpServletRequest request) {
    return generateTokenResponse(request);
  }

  private ResponseEntity<AuthenticationDto.Response.Common> generateTokenResponse(HttpServletRequest request) {
    Claims claims = authenticationService.obtainClaimsFromAuthentication();
    var accessToken = tokenService.generateToken(claims, accessTokenMetadata);
    var refreshToken = tokenService.generateToken(claims, refreshTokenMetadata);

    ProjectionUserDetails userDetails = authenticationService.obtainUserDetailsFromAuthentication();
    saveRefreshSession(new RequestMetadata(request), userDetails.getId(), refreshToken);
    return ResponseEntity.ok(new AuthenticationDto.Response.Common(accessToken, refreshToken));
  }

  private void saveRefreshSession(RequestMetadata requestMetadata, long userId, String refreshToken) {
    var refreshSession = new RefreshSession(userId, requestMetadata.getRemoteAddress(), requestMetadata.getUserAgent(), refreshToken);
    refreshSessionService.save(refreshSession);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request) {
    String refreshToken = Arrays.stream(request.getCookies())
      .filter(cookie -> cookie.getName().equals(refreshTokenMetadata.getHeader()))
      .findAny().orElseThrow(IllegalAccessError::new)
      .getValue();

    refreshSessionService.deleteByToken(refreshToken);
    return ResponseEntity.ok().build();
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handle(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(ex.getMessage());
  }
}
