package com.team.productservice.controller;

import com.team.productservice.data.Product;
import com.team.productservice.dto.ProductResponseDto;
import com.team.productservice.service.ProductService;
import com.team.productservice.service.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
  private final ProductService productService;
  private final ProductMapper productMapper;


  @PostMapping("/api/products/seeddata")
  public ResponseEntity<Product> seedData() {
    productService.seedData();
    return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
  }

  @GetMapping("/api/products")
  public ResponseEntity<List<ProductResponseDto>> getAll() {
    return ResponseEntity.ok().body(
      productService.getAll().stream()
        .map(productMapper::fromCentral)
        .toList()
    );
  }
}