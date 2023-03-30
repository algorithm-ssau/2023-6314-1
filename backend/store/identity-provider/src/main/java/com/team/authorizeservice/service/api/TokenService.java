package com.team.authorizeservice.service.api;

import com.team.authorizeservice.rest.dto.RequestMetadata;
import com.team.authorizeservice.security.details.ProjectionUserDetails;

import java.util.Date;

public interface TokenService {
  TokenGroup generateTokenGroup(ProjectionUserDetails user, RequestMetadata requestMetadata);
  TokenGroup refreshByToken(String refreshToken, RequestMetadata requestMetadata);

  interface DateGroup {
    Date getCreated();
    Date getExpired();
  }

  interface TokenGroup {
    String getAccessToken();
    String getRefreshToken();
  }
}
