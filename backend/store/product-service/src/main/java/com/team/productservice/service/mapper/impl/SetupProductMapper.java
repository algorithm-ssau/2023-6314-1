package com.team.productservice.service.mapper.impl;

import com.team.productservice.data.Product;
import com.team.productservice.rest.ImageServiceClient;
import com.team.productservice.rest.dto.ImageRequestDto;
import com.team.productservice.service.mapper.ObjectMapper;
import com.team.productservice.service.startup.SetupProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SetupProductMapper implements ObjectMapper<SetupProduct, Product> {
  private final ImageServiceClient imageServiceClient;

  @Override
  public Product map(SetupProduct from) {
    List<Long> imageIds = from.getImagePaths().stream()
      .filter(path -> {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
      }).map(path -> {
        ImageRequestDto imageRequestDto1 = readFromFile(path);
        Long savedImageId = imageServiceClient.save(imageRequestDto1).getBody();
        return Objects.requireNonNull(savedImageId);
      }).toList();

    return new Product(
      from.getName(),
      from.getDescription(),
      from.getCost(),
      from.getCountInStock(),
      imageIds
    );
  }

  private ImageRequestDto readFromFile(String filePath) {
    try (FileInputStream fis = new FileInputStream(filePath)) {
      return new ImageRequestDto(fis.readAllBytes());
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
