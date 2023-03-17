package com.team.productservice.startup;

import com.team.productservice.data.Product;
import com.team.productservice.mapper.ProductMapper;
import com.team.productservice.repository.ProductRepository;
import com.team.productservice.rest.ImageServiceClient;
import com.team.productservice.rest.dto.ImageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class SetupDataLoader{
  private boolean firstCall = false;
  private final ImageServiceClient imageServiceClient;
  private final ProductMapper.Startup.Common setupProductMapper;
  private final ProductRepository productRepository;

  @EventListener(ContextRefreshedEvent.class)
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (!firstCall) {
      setup();
      firstCall = true;
    }
  }

  private void setup() {
    if (productRepository.count() == 0) {
      for (SetupProduct setupProduct : SetupProduct.values()) {
        var imagesId = sendImagesToImageService(setupProduct.getImagePaths());
        Product product = setupProductMapper.toDomain(setupProduct, imagesId);
        productRepository.save(product);
      }
    }
  }

  private List<Long> sendImagesToImageService(List<String> paths) {
    List<Long> savedIds = new ArrayList<>();
    for (String path : paths) {
      if (new File(path).exists()) {
        ImageRequestDto imageRequestDto = readFromFile(path);
        Long savedImageId = imageServiceClient.save(imageRequestDto).getBody();
        savedIds.add(savedImageId);
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
