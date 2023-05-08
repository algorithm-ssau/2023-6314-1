package com.team.productservice.service.contract;

import com.team.productservice.model.Category;

import java.util.Set;

public interface CategoryService {
  Long merge(Category category);
  Category findById(Long id);
  Set<Category> findByProjection(Set<Long> ids);
  Category deleteById(Long id);
  Set<Category> findRootSubs();
  Set<Long> findAllSubsToEnd(Long id);
}
