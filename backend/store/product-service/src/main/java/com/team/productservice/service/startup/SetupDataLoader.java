package com.team.productservice.service.startup;

import com.team.productservice.data.Product;
import com.team.productservice.repository.ProductRepository;
import com.team.productservice.service.startup.strategy.SetupStrategy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
  private boolean firstCall = false;
  private final SetupStrategy setupStrategy;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (!firstCall) {
      setupStrategy.setup();
      firstCall = true;
    }
  }
}
