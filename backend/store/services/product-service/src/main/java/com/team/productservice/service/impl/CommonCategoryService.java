package com.team.productservice.service.impl;

import com.team.productservice.model.Category;
import com.team.productservice.infrastructure.repository.CategoryRepository;
import com.team.productservice.service.contract.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

  @Override
  public Set<Category> findRootSubs() {
    Category root = repository.findRoot();
    return root.getSubCategories();
  }

  @Override
  public Set<Long> findAllSubsToEnd(Long id) {
    Category category = findById(id);
    Set<Long> allSubsIds = new HashSet<>();
    allSubsIds.add(category.getId());
    fillSubs(allSubsIds, category);
    return allSubsIds;
  }

  private void fillSubs(Set<Long> ids, Category root) {
    Set<Category> subs = root.getSubCategories();
    ids.addAll(subs.stream().map(Category::getId).collect(Collectors.toSet()));
    subs.forEach(c -> fillSubs(ids, c));
  }
}
