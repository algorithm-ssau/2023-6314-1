package com.team.productservice.controller;

import com.team.productservice.dto.ProductResponseDto;
import com.team.productservice.service.ProductService;
import com.team.productservice.service.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
  private final ProductService productService;
  private final ProductMapper productMapper;

  @GetMapping("/api/products")
  public ResponseEntity<List<ProductResponseDto>> getAll() {
    return ResponseEntity.ok().body(
      productService.getAll().stream()
        .map(productMapper::fromCentral)
        .toList()
    );
  }
}
