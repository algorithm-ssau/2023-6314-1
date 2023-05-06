package com.team.productservice.mapper;

import com.team.productservice.data.Category;
import com.team.productservice.dto.CategoryDto;
import com.team.productservice.service.contract.CategoryService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

public enum CategoryMapper {;
  public enum Request {;
    @Component
    public static final class Common {
      private final CategoryService categoryService;

      public Common(CategoryService categoryService) {
        this.categoryService = categoryService;
      }

      public Category toDomain(CategoryDto.Request.Common dto) {
        return Category.builder()
          .name(dto.getName())
          .parentCategory(categoryService.findById(dto.getParentId()))
          .subCategories(categoryService.findByProjection(dto.getSubs()))
          .build();
      }
    }
  }

  public enum Response {;
    @Component
    public static final class Common {
      public CategoryDto.Response.Common toDto(Category category) {
        return new CategoryDto.Response.Common(
          category.getId(),
          category.getName(),
          category.getParentCategory().getId(),
          category.getSubCategories().stream().map(Category::getId).collect(Collectors.toSet())
        );
      }
    }
  }
}
