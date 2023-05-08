package com.team.identityprovider.security.details;

import com.team.identityprovider.infrastructure.repository.UserRepositoryAuthenticateProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;

public class ProjectionUserDetailsService implements UserDetailsService {
  private final UserRepositoryAuthenticateProjection projectionService;

  @Autowired
  public ProjectionUserDetailsService(UserRepositoryAuthenticateProjection projectionService) {
    this.projectionService = projectionService;
  }

  @Override
  public UserDetails loadUserByUsername(String usernameAsEmail) throws UsernameNotFoundException {
    var userCandidate = projectionService.findByEmail(usernameAsEmail);
    var user = Objects.requireNonNull(userCandidate, "User not present");

    return new ProjectionUserDetails(
      user.getId(),
      user.getName(),
      user.getEmail(),
      user.getPassword(),
      user.isActive(),
      List.of(new SimpleGrantedAuthority(user.getRole()))
    );
  }
}
