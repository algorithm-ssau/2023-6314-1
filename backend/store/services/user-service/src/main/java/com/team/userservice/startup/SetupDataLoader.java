package com.team.userservice.startup;

import com.team.userservice.data.User;
import com.team.userservice.mapper.UserMapper;
import com.team.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
  private boolean firstSetup = false;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper.Startup.Common commonStartupMapper;

  @EventListener(ContextRefreshedEvent.class)
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (!firstSetup) {
      setup();
      firstSetup = true;
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
