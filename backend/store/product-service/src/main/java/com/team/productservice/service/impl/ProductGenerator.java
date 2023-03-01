package com.team.productservice.service.impl;

import com.team.productservice.data.Image;
import com.team.productservice.data.Product;
import com.team.productservice.service.DataGenerator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ProductGenerator implements DataGenerator<Product> {
  private static final String TEST_IMAGE_PATH = "src/main/resources/static/testImage.jpg";
  private long nextGeneratePhase = 0L;

  @Override
  public Product next() {
    Product product = new Product(
      "testName" + nextGeneratePhase,
      "testDescription" + nextGeneratePhase,
      (int) (1000L + nextGeneratePhase),
      nextGeneratePhase,
      List.of(generateImage())
    );
    nextGeneratePhase++;
    return product;
  }

  private Image generateImage() {
    try {
      File imageFile = new File(ProductGenerator.TEST_IMAGE_PATH);
      byte[] imageBytes;
      imageBytes = Files.readAllBytes(imageFile.toPath());
      return new Image(imageBytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
