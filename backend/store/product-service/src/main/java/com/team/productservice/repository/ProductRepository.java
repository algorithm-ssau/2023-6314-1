package com.team.productservice.repository;

import com.team.productservice.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByName(String name);
  boolean existsById(Long id);
}