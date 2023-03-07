package com.team.productservice.service.mapper.impl;

import com.team.productservice.data.Product;
import com.team.productservice.dto.ProductRequestDto;
import com.team.productservice.service.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRequestMapper implements ObjectMapper<ProductRequestDto, Product> {

  @Override
  public Product map(ProductRequestDto from) {
    return new Product(
      from.getName(),
      from.getDescription(),
      from.getCost(),
      from.getCountInStock(),
      from.getImageIds()
    );
  }
}
