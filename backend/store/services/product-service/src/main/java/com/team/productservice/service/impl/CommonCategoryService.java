package com.team.productservice.service.impl;

import com.team.productservice.data.Category;
import com.team.productservice.repository.CategoryRepository;
import com.team.productservice.service.api.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommonCategoryService implements CategoryService {
  private final CategoryRepository repository;

  @Autowired
  public CommonCategoryService(CategoryRepository repository) {
    this.repository = repository;
  }

  @Override
  public Long merge(Category category) {
    Category save = repository.save(category);
    return save.getId();
  }

  @Override
  public Category findById(Long id) {
    return repository.findById(id).orElseThrow(
      () -> new IllegalArgumentException("Category with id: " + id + " not found!")
    );
  }

  @Override
  public Set<Category> findByProjection(Set<Long> ids) {
    return repository.findProjection(ids);
  }

  @Override
  public Category deleteById(Long id) {
    Category deleted = repository.findById(id).orElseThrow(
      () -> new IllegalArgumentException("Category with id " + id + " not found!"));
    repository.deleteById(id);
    return deleted;
  }
}
