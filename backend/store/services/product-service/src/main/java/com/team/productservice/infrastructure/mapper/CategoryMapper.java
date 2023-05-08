package com.team.productservice.infrastructure.mapper;

import com.team.productservice.model.Category;
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
        Long parentId = dto.getParentId();
        Category parentCategory = null;
        if (parentId != null) {
          parentCategory = categoryService.findById(parentId);
        }
        return Category.builder()
          .name(dto.getName())
          .parentCategory(parentCategory)
          .subCategories(categoryService.findByProjection(dto.getSubs()))
          .build();
      }
    }
  }

  public enum Response {;
    @Component
    public static final class Common {
      public CategoryDto.Response.Common toDto(Category category) {
        Category parentCategory = category.getParentCategory();
        Long parentId = null;
        if (parentCategory != null) {
          parentId = parentCategory.getId();
        }
        return new CategoryDto.Response.Common(
          category.getId(),
          category.getName(),
          parentId,
          category.getSubCategories().stream().map(Category::getId).collect(Collectors.toSet())
        );
      }
    }
  }
}
