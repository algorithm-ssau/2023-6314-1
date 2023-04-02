package com.team.identityprovider.service.api;

import com.team.identityprovider.rest.dto.RequestMetadata;
import com.team.identityprovider.security.details.ProjectionUserDetails;

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
