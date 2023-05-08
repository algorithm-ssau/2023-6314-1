package com.team.productservice.infrastructure.repository;

import com.team.productservice.model.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsById(@NonNull Long id);

  @Query(nativeQuery = true, value = "select * from products where category in ?1")
  List<Product> findAllByCategories(Collection<Long> categories);
}