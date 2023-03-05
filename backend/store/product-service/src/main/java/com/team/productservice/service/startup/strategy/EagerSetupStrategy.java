package com.team.productservice.service.startup.strategy;

import com.team.productservice.data.Product;
import com.team.productservice.repository.ProductRepository;
import com.team.productservice.service.startup.SetupProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class EagerSetupStrategy extends SetupStrategy {

  @Autowired
  public EagerSetupStrategy(ProductRepository productRepository) {
    super(productRepository);
  }

  @Override
  public void setup() {
    for (SetupProducts testProduct : SetupProducts.values()) {
      Product product = testProduct.toValue();
      if (!productRepository.existsByName(product.getName())) {
        productRepository.save(product);
      }
    }
  }
}
