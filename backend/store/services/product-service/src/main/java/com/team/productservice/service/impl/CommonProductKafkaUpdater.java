package com.team.productservice.service.impl;

import com.team.productservice.model.Product;
import com.team.productservice.service.contract.ProductService;
import com.team.productservice.service.contract.ProductKafkaUpdater;
import org.springframework.stereotype.Service;

@Service
public class CommonProductKafkaUpdater implements ProductKafkaUpdater {
  private final ProductService productService;

  public CommonProductKafkaUpdater(ProductService productService) {
    this.productService = productService;
  }

  @Override
  public void updateQuantity(Long id, Long delta) {
    Product product = productService.getById(id);
    product.setCountInStock(product.getCountInStock() + delta);
    productService.update(product);
  }
}
