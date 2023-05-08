package com.team.productservice.service.contract;

import com.team.productservice.model.Product;

import java.util.List;

public interface ProductService {
  List<Product> getAll();
  Product getById(Long id);
  void deleteById(Long id);
  void save(Product product);
  void update(Product product);
  List<Product> findAllByCategoryId(Long categoryId);
}
