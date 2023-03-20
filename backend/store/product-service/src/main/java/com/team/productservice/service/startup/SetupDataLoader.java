package com.team.productservice.service.startup;

import com.team.productservice.data.Product;
import com.team.productservice.repository.ProductRepository;
import com.team.productservice.service.mapper.impl.SetupProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class SetupDataLoader{
  private boolean firstCall = false;
  private final SetupProductMapper setupProductMapper;
  private final ProductRepository productRepository;

  @EventListener(ContextRefreshedEvent.class)
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (!firstCall) {
      firstCall = true;
      setup();
    }
  }

  private void setup() {
    if (productRepository.count() == 0) {
      for (SetupProduct setupProduct : SetupProduct.values()) {
        Product product = setupProductMapper.map(setupProduct);
        productRepository.save(product);
      }
    }
  }
}
