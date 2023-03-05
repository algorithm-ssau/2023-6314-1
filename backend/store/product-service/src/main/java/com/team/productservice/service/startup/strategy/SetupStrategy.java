package com.team.productservice.service.startup.strategy;

import com.team.productservice.repository.ProductRepository;

public abstract class SetupStrategy {
  protected final ProductRepository productRepository;

  public SetupStrategy(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  abstract public void setup();
}
