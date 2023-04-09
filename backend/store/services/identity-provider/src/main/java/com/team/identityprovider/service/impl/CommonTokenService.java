package com.team.identityprovider.service.impl;

import com.team.identityprovider.persistence.model.RefreshSession;
import com.team.identityprovider.rest.dto.RequestMetadata;
import com.team.identityprovider.security.details.ProjectionUserDetails;
import com.team.identityprovider.security.jwt.authentication.JwtSecurityProvider;
import com.team.identityprovider.security.jwt.builder.api.TokenBuilder;
import com.team.identityprovider.security.jwt.properties.TokenPropertiesExtractor;
import com.team.identityprovider.service.contract.RefreshSessionService;
import com.team.identityprovider.service.contract.TokenService;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@Transactional
public class CommonTokenService implements TokenService {
  private final RefreshSessionService refreshSessionService;
  private final JwtSecurityProvider jwtSecurityProvider;
  private final TokenBuilder tokenBuilder;
  private final TokenPropertiesExtractor.TokenData accessTokenData;
  private final TokenPropertiesExtractor.TokenData refreshTokenData;

  @Autowired
  public CommonTokenService(RefreshSessionService refreshSessionService,
                            JwtSecurityProvider jwtSecurityProvider,
                            TokenBuilder tokenBuilder,
                            TokenPropertiesExtractor tokenPropertiesExtractor) {
    this.refreshSessionService = refreshSessionService;
    this.tokenBuilder = tokenBuilder;
    this.jwtSecurityProvider = jwtSecurityProvider;
    this.accessTokenData = tokenPropertiesExtractor.pullAccessTokenData();
    this.refreshTokenData = tokenPropertiesExtractor.pullRefreshTokenData();
  }

  @lombok.Value
  private static class CommonDateGroup implements DateGroup {
    @PastOrPresent Date created = new Date();
    @FutureOrPresent Date expired;

    public CommonDateGroup(Long expiredInMls) {
      this.expired = new Date(created.getTime() + expiredInMls);
    }
  }

  @lombok.Value
  public static class CommonTokenGroup implements TokenGroup {
    @NotBlank String accessToken;
    @NotBlank String refreshToken;
  }

  @Override
  public CommonTokenGroup generateTokenGroup(ProjectionUserDetails user, RequestMetadata requestMetadata) {
    var claims = tokenBuilder.buildJwtClaims(user.getEmail(), user.getAuthorities());
    var accessDateGroup = new CommonDateGroup(accessTokenData.getValidityDateInMilliseconds());
    var refreshDateGroup = new CommonDateGroup(refreshTokenData.getValidityDateInMilliseconds());
    var tokenGroup = new CommonTokenGroup(
      tokenBuilder.buildTokenBody(claims, accessTokenData.getSecretKey(), accessDateGroup),
      tokenBuilder.buildTokenBody(claims, refreshTokenData.getSecretKey(), refreshDateGroup)
    );
    log.debug("Generated token group: {}", tokenGroup);

    refreshSessionService.save(
      new RefreshSession(
        user.getId(),
        requestMetadata.getRemoteAddress(),
        requestMetadata.getUserAgent(),
        refreshDateGroup.getExpired(),
        tokenGroup.getRefreshToken()
      )
    );
    return tokenGroup;
  }

  @Override
  public TokenGroup refreshByToken(String refreshToken, RequestMetadata requestMetadata) {
    Authentication authentication = jwtSecurityProvider.loadAuthenticationByRefreshToken(refreshToken);
    return generateTokenGroup(
      (ProjectionUserDetails) authentication.getPrincipal(),
      requestMetadata
    );
  }
}
