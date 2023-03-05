package com.team.productservice.service.startup.strategy;

import com.team.productservice.data.Product;
import com.team.productservice.repository.ProductRepository;
import com.team.productservice.service.startup.SetupProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class LazySetupStrategy extends SetupStrategy {

  @Autowired
  public LazySetupStrategy(ProductRepository productRepository) {
    super(productRepository);
  }

  @Override
  public void setup() {
    if (productRepository.count() == 0) {
      for (SetupProduct testProduct : SetupProduct.values()) {
        Product product = testProduct.toValue();
        productRepository.save(product);
      }
    }
  }
}
