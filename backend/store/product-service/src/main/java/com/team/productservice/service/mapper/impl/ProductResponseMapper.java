package com.team.productservice.service.mapper.impl;

import com.team.productservice.data.Product;
import com.team.productservice.dto.ProductResponseDto;
import com.team.productservice.service.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class ProductResponseMapper implements ObjectMapper<Product, ProductResponseDto> {
  private final ImageResponseMapper imageResponseMapper;

  @Override
  public ProductResponseDto map(Product from) {
    return new ProductResponseDto(
      from.getId(),
      from.getName(),
      from.getDescription(),
      from.getCost(),
      from.getCountInStock(),
      from.getImages().stream()
        .map(imageResponseMapper::map)
        .toList()
    );
  }
}
