package com.team.userservice.startup;

import com.team.userservice.model.User;
import com.team.userservice.mapper.UserMapper;
import com.team.userservice.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Slf4j
public class SetupDataLoader {
  private boolean firstSetup = false;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper.Startup.Common commonStartupMapper;

  @Autowired
  public SetupDataLoader(PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper.Startup.Common commonStartupMapper) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.commonStartupMapper = commonStartupMapper;
  }

  @EventListener(ContextRefreshedEvent.class)
  public void startInitializing(){
    if (!firstSetup) {
      firstSetup = true;
      setup();
    }
  }

  private void setup() {
    long usersCount = userRepository.count();
    if (usersCount == 0) {
      for (SetupUser setupUser : SetupUser.values()) {
        var encodedPassword = passwordEncoder.encode(setupUser.getPassword());
        User user = commonStartupMapper.toDomain(setupUser, encodedPassword);
        userRepository.save(user);
      }
    }
  }
}
