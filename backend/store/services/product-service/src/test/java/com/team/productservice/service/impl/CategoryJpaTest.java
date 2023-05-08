package com.team.productservice.service.impl;

import com.team.productservice.model.Category;
import com.team.productservice.infrastructure.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryJpaTest {
  @Autowired
  private CategoryRepository categoryRepository;

  private static final List<Category> rootCategories = new ArrayList<>();

  @BeforeEach
  void beforeEach() {
    Category electronicsCategory = new Category("Electronics");
    electronicsCategory.addSub("Smartphones");
    electronicsCategory.addSub("Blenders");

    Category clothesCategory = new Category("Clothes");
    clothesCategory.addSub("Shoes");
    clothesCategory.addSub("Outerwear");

    Category sportingGoodsCategory = new Category("Sporting goods");
    sportingGoodsCategory.addSub("Bicycles");
    sportingGoodsCategory.addSub("Simulators");
    sportingGoodsCategory.addSub("Balls");

    rootCategories.clear();
    rootCategories.add(electronicsCategory);
    rootCategories.add(clothesCategory);
    rootCategories.add(sportingGoodsCategory);
  }

  @Test
  void shouldAdd() {
    List<Category> savedCategories = categoryRepository.saveAll(rootCategories);
    Category anyCategory = savedCategories.get(0);
    Category anySubCategory = anyCategory.getSubCategories().stream().toList().get(0);
    assertTrue(() -> anyCategory.getSubCategories().contains(anySubCategory));
  }

  @Test
  void shouldReturnValidProjection() {
    Set<Long> ids = categoryRepository.saveAll(rootCategories).stream()
      .map(Category::getId)
      .collect(Collectors.toSet());
    Set<Category> projection = categoryRepository.findProjection(ids);

    assertTrue(equalsSets(ids, projection.stream().map(Category::getId).collect(Collectors.toSet())));
  }

  private <T> boolean equalsSets(Set<T> set1, Set<T> set2) {
    boolean equals = true;
    for (T t : set1) {
      equals = set2.contains(t);
      if (!equals) {
        break;
      }
    }
    return equals;
  }
}