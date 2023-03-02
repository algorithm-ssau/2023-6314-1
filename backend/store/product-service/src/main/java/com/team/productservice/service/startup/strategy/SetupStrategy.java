package com.team.productservice.service.startup.strategy;

import com.team.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SetupStrategy {
  protected final ProductRepository productRepository;

  @Autowired
  public SetupStrategy(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  abstract public void setup();
}
