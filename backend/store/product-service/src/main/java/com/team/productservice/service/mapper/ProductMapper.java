package com.team.productservice.service.mapper;

import com.team.productservice.data.Product;
import com.team.productservice.dto.ProductRequestDto;
import com.team.productservice.dto.ProductResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper implements UnidirectionalObjectMapper<ProductRequestDto, Product, ProductResponseDto> {
  @Override
  public Product toCentral(ProductRequestDto from) {
    return null;
  }

  @Override
  public ProductResponseDto fromCentral(Product central) {
    ProductResponseDto responseDto = new ProductResponseDto();
    responseDto.setId(central.getId());
    responseDto.setName(central.getName());
    responseDto.setCountInStock(central.getCountInStock());
    responseDto.setImage(central.getImages().get(0).getContent());
    return responseDto;
  }
}
