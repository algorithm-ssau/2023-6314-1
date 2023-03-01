package com.team.productservice.service;

import com.team.productservice.data.Product;

import java.util.List;

public interface ProductService {
  void seedData();
  List<Product> getAll();
}
