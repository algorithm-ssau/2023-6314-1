package com.team.authorizeservice.service.api;

import com.team.authorizeservice.security.details.ProjectionUserDetails;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
  ProjectionUserDetails authenticate(String email, String password);
}
