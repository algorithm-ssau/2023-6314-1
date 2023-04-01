package com.team.identityprovider.service.api;

import com.team.identityprovider.security.details.ProjectionUserDetails;

public interface AuthenticationService {
  ProjectionUserDetails authenticate(String email, String password);
}
