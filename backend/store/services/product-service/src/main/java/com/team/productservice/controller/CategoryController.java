package com.team.productservice.controller;

import com.team.productservice.model.Category;
import com.team.productservice.dto.CategoryDto;
import com.team.productservice.infrastructure.mapper.CategoryMapper;
import com.team.productservice.service.contract.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;
  private final CategoryMapper.Response.Common responseMapper;

  @Autowired
  public CategoryController(CategoryService categoryService,
                            CategoryMapper.Response.Common responseMapper) {
    this.categoryService = categoryService;
    this.responseMapper = responseMapper;
  }

  @GetMapping("/root")
  public ResponseEntity<Set<CategoryDto.Response.Common>> getRoot() {
    Set<CategoryDto.Response.Common> rootSubs = categoryService.findRootSubs().stream()
      .map(responseMapper::toDto)
      .collect(Collectors.toSet());
    return ResponseEntity.ok(rootSubs);
  }

  @GetMapping("/{id}/subs")
  public ResponseEntity<Set<CategoryDto.Response.Common>> getSubsById(@PathVariable Long id) {
    Category category = categoryService.findById(id);
    Set<CategoryDto.Response.Common> subs = category.getSubCategories().stream()
      .map(responseMapper::toDto)
      .collect(Collectors.toSet());
    return ResponseEntity.ok(subs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto.Response.Common> getCategory(@PathVariable Long id) {
    CategoryDto.Response.Common dto = responseMapper.toDto(categoryService.findById(id));
    return ResponseEntity.ok(dto);
  }
}
