package com.team.productservice.service.impl;

import com.team.productservice.data.Product;
import com.team.productservice.exception.ProductNotFoundException;
import com.team.productservice.repository.ProductRepository;
import com.team.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CommonProductService implements ProductService {
  private final ProductRepository productRepository;

  @Override
  public List<Product> getAll() {
    return productRepository.findAll();
  }

  @Override
  public Product getById(Long id) {
    return productRepository.findById(id)
      .orElseThrow(() -> new ProductNotFoundException("Product with id: " + id + " not found"));
  }

  @Override
  public void deleteById(Long id) {
    productRepository.deleteById(id);
  }

  @Override
  public void save(Product product) {
    productRepository.save(product);
  }

  @Override
  public void update(Product product) {
    Long productId = product.getId();
    if (productId == null) {
      throw new ProductNotFoundException("Cannot update product without id");
    } else if (!productRepository.existsById(productId)) {
      throw new ProductNotFoundException("Cannot update product with id: " + productId + ", product not found");
    }
    save(product);
  }
}