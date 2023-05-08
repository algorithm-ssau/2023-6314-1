package com.team.productservice.mapper;

import com.team.productservice.model.Category;
import org.junit.jupiter.api.Test;

import static com.team.productservice.startup.SetupCategory.SHOES;
import static com.team.productservice.startup.SetupCategory.by;
import static org.junit.jupiter.api.Assertions.*;

class SetupCategoryTest {

  @Test
  void shouldMap() {
    Category category = by("All.Male.Adult.Shoes");

    assertNotNull(category);
    assertEquals(category.getName(), SHOES.getName());
  }

  @Test
  void shouldContainParentAndSubcategories() {
    Category parent = by("All.Male");
    Category category = by("All.Male.Adult");
    Category subCategory = by("All.Male.Adult.Accessories");

    assertEquals(category.getParentCategory(), parent);
    assertTrue(category.getSubCategories().contains(subCategory));
    assertTrue(parent.getSubCategories().contains(category));
    assertEquals(subCategory.getParentCategory(), category);
  }
}