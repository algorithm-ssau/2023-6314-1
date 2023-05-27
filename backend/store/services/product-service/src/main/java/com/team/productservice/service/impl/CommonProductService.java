package com.team.productservice.service.impl;

import com.team.productservice.model.Product;
import com.team.productservice.model.exception.ProductNotFoundException;
import com.team.productservice.infrastructure.repository.ProductRepository;
import com.team.productservice.service.contract.CategoryService;
import com.team.productservice.service.contract.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class CommonProductService implements ProductService {
  private final ProductRepository productRepository;
  private final CategoryService categoryService;

  @Override
  public List<Product> getAll() {
    return productRepository.findAll().stream()
      .peek(product -> product.setImageIds(List.of(product.getImageIds().get(0))))
      .toList();
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

  @Override
  public List<Product> findAllByCategoryId(Long categoryId) {
    Set<Long> allSubsIdsToEnd = categoryService.findAllSubsToEnd(categoryId);
    return productRepository.findAllByCategories(allSubsIdsToEnd);
  }
}