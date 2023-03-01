package com.team.productservice.service.impl;

import com.team.productservice.data.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductGeneratorTest {
  private final ProductGenerator productGenerator = new ProductGenerator();

  @Test
  void next_shouldBe() {
    Product testProduct = productGenerator.next();

    assertNotNull(testProduct);
  }

  @Test
  void next_shouldIncreasePhase() {
    Product testProduct1 = productGenerator.next();
    Product testProduct2 = productGenerator.next();

    assertEquals(testProduct1.getCountInStock(), testProduct2.getCountInStock() - 1);
  }

  @Test
  void next_testImageShouldPresent() {
    assertDoesNotThrow(productGenerator::next);
  }
}