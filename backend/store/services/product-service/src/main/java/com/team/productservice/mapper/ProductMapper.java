package com.team.productservice.mapper;

import com.team.productservice.data.Product;
import com.team.productservice.dto.ProductDto;
import com.team.productservice.startup.SetupProduct;
import org.springframework.stereotype.Component;

import java.util.List;

public enum ProductMapper {;
  public enum Request {;
    @Component
    public static final class Common {
      public Product toDomain(ProductDto.Request.Common dto, List<Long> imagesId) {
        return new Product(
          dto.getName(),
          dto.getDescription(),
          dto.getCost(),
          dto.getCountInStock(),
          imagesId
        );
      }
    }

    @Component
    public static final class Create {
      public Product toDomain(ProductDto.Request.Create dto, List<Long> imagesId) {
        return new Product(
          dto.getName(),
          dto.getDescription(),
          dto.getCost(),
          dto.getCountInStock(),
          imagesId
        );
      }
    }
  }

  public enum Response {;
    @Component
    public static final class Common {
      public ProductDto.Response.Common toDto(Product product, byte[][] imagesBytes) {
        return new ProductDto.Response.Common(
          product.getId(),
          product.getName(),
          product.getDescription(),
          product.getCost(),
          product.getCountInStock(),
          imagesBytes
        );
      }
    }
  }

  public enum Startup {;
    @Component
    public static final class Common {
      public Product toDomain(SetupProduct setupProduct, List<Long> imagesId) {
        return new Product(
          setupProduct.getName(),
          setupProduct.getDescription(),
          setupProduct.getCost(),
          setupProduct.getCountInStock(),
          imagesId
        );
      }
    }
  }
}
