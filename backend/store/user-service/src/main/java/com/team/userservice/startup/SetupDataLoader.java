package com.team.userservice.startup;

import com.team.userservice.data.User;
import com.team.userservice.repository.UserRepository;
import com.team.userservice.mapper.impl.SetupUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
  private boolean firstSetup = false;
  private final UserRepository userRepository;
  private final SetupUserMapper setupUserMapper;

  @Override
  @Transactional
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
        User user = setupUserMapper.map(setupUser);
        userRepository.save(user);
      }
    }
  }
}
