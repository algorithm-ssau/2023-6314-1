package com.team.identityprovider.view.controller;

import com.team.identityprovider.model.RefreshSession;
import com.team.identityprovider.security.details.ProjectionUserDetails;
import com.team.identityprovider.service.contract.RefreshSessionService;
import com.team.identityprovider.service.contract.TokenService;
import com.team.identityprovider.service.impl.UsernamePasswordAuthenticationService;
import com.team.identityprovider.view.dto.AuthenticationDto;
import com.team.identityprovider.view.dto.RequestMetadata;
import com.team.identityprovider.view.resolve.HttpServletRequestResolver;
import com.team.jwt.properties.TokenMetadata;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  private final TokenService tokenService;
  private final RefreshSessionService refreshSessionService;
  private final UsernamePasswordAuthenticationService authService;
  private final TokenMetadata accessMetadata;
  private final TokenMetadata refreshMetadata;
  private final HttpServletRequestResolver httpResolver;

  @Autowired
  public AuthenticationController(TokenService tokenService,
                                  RefreshSessionService sessionService,
                                  UsernamePasswordAuthenticationService authService,
                                  TokenMetadata accessMetadata,
                                  TokenMetadata refreshMetadata,
                                  HttpServletRequestResolver httpResolver) {
    this.tokenService = tokenService;
    this.refreshSessionService = sessionService;
    this.authService = authService;
    this.accessMetadata = accessMetadata;
    this.refreshMetadata = refreshMetadata;
    this.httpResolver = httpResolver;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDto.Request.Common dto,
                                 HttpServletRequest request) {
    authService.authenticate(dto.getEmail(), dto.getPassword());
    Claims claims = authService.obtainClaimsFromAuthentication();

    String accessToken = tokenService.generateToken(claims, accessMetadata);
    String refreshToken = tokenService.generateToken(claims, refreshMetadata);

    ProjectionUserDetails userDetails = authService.obtainUserDetailsFromAuthentication();
    RequestMetadata requestMetadata = new RequestMetadata(request);
    Long userId = userDetails.getId();
    Date expired = refreshMetadata.expiredInterval().getExpired();
    saveRefreshSession(requestMetadata, userId, refreshToken, expired);
    
    return ResponseEntity.ok(new AuthenticationDto.Response.Common(accessToken, refreshToken));
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(HttpServletRequest request) {
    String oldRefreshToken = httpResolver.getTokenFromCookie(request, refreshMetadata);
    RefreshSession presentSession = refreshSessionService.findByToken(oldRefreshToken);
    Date expired = presentSession.getExpired();
    String header = refreshMetadata.getHeader();
    Key secretKey = refreshMetadata.getSecretKey();
    Claims claims = authService.obtainClaimsFromAuthentication();
    TokenMetadata newRefreshMetadata = new TokenMetadata(header, secretKey, expired);

    String accessToken = tokenService.generateToken(claims, accessMetadata);
    String refreshToken = tokenService.generateToken(claims, newRefreshMetadata);

    ProjectionUserDetails userDetails = authService.obtainUserDetailsFromAuthentication();
    RequestMetadata requestMetadata = new RequestMetadata(request);
    Long userId = userDetails.getId();
    saveRefreshSession(requestMetadata, userId, refreshToken, expired);

    return ResponseEntity.ok(new AuthenticationDto.Response.Common(accessToken, refreshToken));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request) {
    String refreshToken = Arrays.stream(request.getCookies())
      .filter(cookie -> cookie.getName().equals(refreshMetadata.getHeader()))
      .findAny().orElseThrow(IllegalAccessError::new)
      .getValue();

    refreshSessionService.deleteByToken(refreshToken);
    return ResponseEntity.ok().build();
  }

  private void saveRefreshSession(RequestMetadata requestMetadata,
                                  long userId,
                                  String refreshToken,
                                  Date expired) {
    var refreshSession = new RefreshSession(
      userId,
      requestMetadata.getRemoteAddress(),
      requestMetadata.getUserAgent(),
      refreshToken,
      expired
    );
    refreshSessionService.save(refreshSession);
  }
}
