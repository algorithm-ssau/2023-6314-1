package com.team.orderservice.startup;

import com.team.orderservice.model.Order;
import com.team.orderservice.mapper.OrderMapper;
import com.team.orderservice.infrastructure.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class SetupDataLoader {
  private boolean firstSetup = false;
  private final OrderRepository orderRepository;
  private final OrderMapper.Startup.Common commonStartupMapper;

  @EventListener(ContextRefreshedEvent.class)
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
        Order order = commonStartupMapper.toDomain(setupOrder);
        orderRepository.save(order);
      }
    }
  }
}
