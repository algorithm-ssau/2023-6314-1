package com.team.productservice.service;

import com.team.productservice.data.Product;

import java.util.List;

public interface ProductService {
  List<Product> getAll();
  Product getById(Long id);
  void deleteById(Long id);
  void save(Product product);
  void update(Product product);
}
