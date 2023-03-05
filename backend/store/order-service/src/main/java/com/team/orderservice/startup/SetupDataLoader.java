package com.team.orderservice.startup;

import com.team.orderservice.data.Order;
import com.team.orderservice.mapper.impl.SetupOrderMapper;
import com.team.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
  private boolean firstSetup = false;
  private final OrderRepository orderRepository;
  private final SetupOrderMapper setupOrderMapper;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (!firstSetup) {
      setup();
      firstSetup = true;
    }
  }

  private void setup() {
    long ordersCount = orderRepository.count();
    if (ordersCount == 0) {
      for (SetupOrder setupOrder : SetupOrder.values()) {
        Order order = setupOrderMapper.map(setupOrder);
        orderRepository.save(order);
      }
    }
  }
}
