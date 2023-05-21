package com.team.productservice.view.controller;

import com.team.productservice.model.Category;
import com.team.productservice.service.contract.CategoryService;
import com.team.productservice.view.MapperFacade;
import com.team.productservice.view.dto.CategoryDto;
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
  private final MapperFacade mapperFacade;

  @Autowired
  public CategoryController(CategoryService categoryService, MapperFacade mapperFacade) {
    this.categoryService = categoryService;
    this.mapperFacade = mapperFacade;
  }

  @GetMapping("/root")
  public ResponseEntity<Set<CategoryDto.Response.Common>> getRoot() {
    Set<CategoryDto.Response.Common> rootSubs = categoryService.findRootSubs().stream()
      .map(mapperFacade::toCommonResponseCategoryDto)
      .collect(Collectors.toSet());
    return ResponseEntity.ok(rootSubs);
  }

  @GetMapping("/{id}/subs")
  public ResponseEntity<Set<CategoryDto.Response.Common>> getSubsById(@PathVariable Long id) {
    Category category = categoryService.findById(id);
    Set<CategoryDto.Response.Common> subs = category.getSubCategories().stream()
      .map(mapperFacade::toCommonResponseCategoryDto)
      .collect(Collectors.toSet());
    return ResponseEntity.ok(subs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto.Response.Common> getCategory(@PathVariable Long id) {
    Category presentCategory = categoryService.findById(id);
    CategoryDto.Response.Common dto = mapperFacade.toCommonResponseCategoryDto(presentCategory);
    return ResponseEntity.ok(dto);
  }
}
