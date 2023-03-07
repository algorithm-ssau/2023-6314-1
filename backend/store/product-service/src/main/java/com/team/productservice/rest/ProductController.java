package com.team.productservice.rest;

import com.team.productservice.data.Product;
import com.team.productservice.dto.ProductRequestDto;
import com.team.productservice.dto.ProductResponseDto;
import com.team.productservice.service.ProductService;
import com.team.productservice.service.mapper.impl.ProductRequestMapper;
import com.team.productservice.service.mapper.impl.ProductResponseMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products/")
public class ProductController {
  private final ProductService productService;
  private final ProductResponseMapper productResponseMapper;
  private final ProductRequestMapper productRequestMapper;

  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> getAll() {
    return ResponseEntity.ok().body(
      productService.getAll().stream()
        .map(productResponseMapper::map)
        .toList()
    );
  }

  @GetMapping("{id}")
  public ResponseEntity<ProductResponseDto> get(@PathVariable Long id) {
    Product product = productService.getById(id);
    ProductResponseDto productResponseDto = productResponseMapper.map(product);
    return ResponseEntity.ok().body(productResponseDto);
  }

  @PostMapping
  public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductRequestDto productRequestDto) {
    Product product = productRequestMapper.map(productRequestDto);
    productService.save(product);
    return ResponseEntity.ok().build();
  }

  @PutMapping("{id}")
  public ResponseEntity<ProductResponseDto> update(@PathVariable Long id,
                                                   @Valid @RequestBody ProductRequestDto productRequestDto) {
    Product product = productRequestMapper.map(productRequestDto);
    product.setId(id);
    productService.update(product);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<ProductResponseDto> delete(@PathVariable Long id) {
    productService.deleteById(id);
    return ResponseEntity.ok().build();
  }


}
