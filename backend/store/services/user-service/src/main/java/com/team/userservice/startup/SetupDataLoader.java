package com.team.userservice.startup;

import com.team.userservice.data.User;
import com.team.userservice.mapper.UserMapper;
import com.team.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
@Slf4j
public class SetupDataLoader {
  private boolean firstSetup = false;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper.Startup.Common commonStartupMapper;

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
