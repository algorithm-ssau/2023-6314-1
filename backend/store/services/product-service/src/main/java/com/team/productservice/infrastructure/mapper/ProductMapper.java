package com.team.productservice.infrastructure.mapper;

import com.team.productservice.model.Category;
import com.team.productservice.model.Product;
import com.team.productservice.dto.ProductDto;
import com.team.productservice.startup.SetupProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public enum ProductMapper {;
  public enum Request {;
    @Component
    public static final class Common {
      private final CategoryMapper.Request.Common requestMapper;

      @Autowired
      public Common(CategoryMapper.Request.Common requestMapper) {
        this.requestMapper = requestMapper;
      }

      public Product toDomain(ProductDto.Request.Common dto, List<Long> imagesId) {
        return Product.builder()
          .name(dto.getName())
          .description(dto.getDescription())
          .cost(dto.getCost())
          .countInStock(dto.getCountInStock())
          .imageIds(imagesId)
          .category(requestMapper.toDomain(dto.getCategory()))
          .build();
      }
    }

    @Component
    public static final class Create {
      public Product toDomain(ProductDto.Request.Create dto, List<Long> imagesId, Category category) {
        return Product.builder()
          .name(dto.getName())
          .description(dto.getDescription())
          .cost(dto.getCost())
          .countInStock(dto.getCountInStock())
          .imageIds(imagesId)
          .category(category)
          .build();
      }
    }

    @Component
    public static final class Update {
      private final CategoryMapper.Request.Common mapper;

      @Autowired
      public Update(CategoryMapper.Request.Common mapper) {
        this.mapper = mapper;
      }

      public Product toDomain(ProductDto.Request.Update dto, List<Long> imagesId) {
        return Product.builder()
          .name(dto.getName())
          .description(dto.getDescription())
          .cost(dto.getCost())
          .countInStock(dto.getCountInStock())
          .imageIds(imagesId)
          .category(mapper.toDomain(dto.getCategory()))
          .build();
      }
    }
  }

  public enum Response {;
    @Component
    public static final class Common {
      private final CategoryMapper.Response.Common mapper;

      @Autowired
      public Common(CategoryMapper.Response.Common mapper) {
        this.mapper = mapper;
      }

      public ProductDto.Response.Common toDto(Product product, List<String> contents) {
        return new ProductDto.Response.Common(
          product.getId(),
          product.getName(),
          product.getDescription(),
          product.getCost(),
          product.getCountInStock(),
          contents,
          mapper.toDto(product.getCategory())
        );
      }

      public ProductDto.Response.Common toDto(Product product, String... contents) {
        return new ProductDto.Response.Common(
          product.getId(),
          product.getName(),
          product.getDescription(),
          product.getCost(),
          product.getCountInStock(),
          List.of(contents),
          mapper.toDto(product.getCategory())
        );
      }
    }
  }

  public enum Startup {;
    @Component
    public static final class Common {
      public Product toDomain(SetupProduct setupProduct, List<Long> imagesId) {
        return Product.builder()
          .name(setupProduct.getName())
          .description(setupProduct.getDescription())
          .cost(setupProduct.getCost())
          .countInStock(setupProduct.getCountInStock())
          .imageIds(imagesId)
          .category(setupProduct.getCategory())
          .build();
      }
    }
  }
}
