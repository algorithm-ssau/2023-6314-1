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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SetupProductMapper implements ObjectMapper<SetupProduct, Product> {
  private final ImageServiceClient imageServiceClient;

  @Override
  public Product map(SetupProduct from) {
    return new Product(
      from.getName(),
      from.getDescription(),
      from.getCost(),
      from.getCountInStock(),
      saveImages(from.getImagePaths())
    );
  }

  private List<Long> saveImages(List<String> paths) {
    List<Long> savedIds = new ArrayList<>();
    for (String path : paths) {
      if (new File(path).exists()) {
        ImageRequestDto imageRequestDto = readFromFile(path);
        Long savedImageId = imageServiceClient.save(imageRequestDto).getBody();
        savedIds.add(savedImageId);
      } else {
        System.out.println("Image" + path + " not found");
      }
    }
    return savedIds;
  }

  private ImageRequestDto readFromFile(String filePath) {
    try (FileInputStream fis = new FileInputStream(filePath)) {
      return new ImageRequestDto(fis.readAllBytes());
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
