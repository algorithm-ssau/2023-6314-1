package com.team.authorizeservice.security.details;

import com.team.authorizeservice.persistence.model.User;
import com.team.authorizeservice.persistence.repository.UserRepositoryAuthenticateProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProjectionUserDetailsService implements UserDetailsService {
  private final UserRepositoryAuthenticateProjection projectionService;

  @Autowired
  public ProjectionUserDetailsService(UserRepositoryAuthenticateProjection projectionService) {
    this.projectionService = projectionService;
  }

  @Override
  public UserDetails loadUserByUsername(String usernameAsEmail) throws UsernameNotFoundException {
    var maybeNullUserAuth = projectionService.findByEmail(usernameAsEmail);
    var userAuth = Objects.requireNonNull(maybeNullUserAuth, "User not present");
    log.info("User with username: {} successfully loaded from database", usernameAsEmail);

    return new ProjectionUserDetails(
      userAuth.getId(),
      userAuth.getName(),
      userAuth.getEmail(),
      userAuth.getPassword(),
      userAuth.isActive(),
      List.of(new SimpleGrantedAuthority(userAuth.getRole()))
    );
  }
}
