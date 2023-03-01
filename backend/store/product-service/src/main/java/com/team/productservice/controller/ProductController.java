package com.team.productservice.controller;

import com.team.productservice.data.Product;
import com.team.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping("/api/products/seeddata")
  public ResponseEntity<Product> seedData() {
    productService.seedData();
    return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
  }
}
