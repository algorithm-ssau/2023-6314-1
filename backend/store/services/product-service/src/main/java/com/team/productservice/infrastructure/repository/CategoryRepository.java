package com.team.productservice.infrastructure.repository;

import com.team.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  @Query(nativeQuery = true, value = "select * from categories where category_id in ?1")
  Set<Category> findProjection(Set<Long> ids);

  @Query(nativeQuery = true, value = "select * from categories where parent is null")
  Category findRoot();
}
