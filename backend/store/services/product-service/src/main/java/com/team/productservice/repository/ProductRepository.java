package com.team.productservice.repository;

import com.team.productservice.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsById(Long id);
}