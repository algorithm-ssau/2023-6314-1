package com.team.productservice.startup;

import com.team.productservice.model.Product;
import com.team.productservice.infrastructure.mapper.ProductMapper;
import com.team.productservice.infrastructure.repository.ProductRepository;
import com.team.productservice.infrastructure.external.ImageServiceClient;
import com.team.productservice.service.impl.Base64ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Transactional
@Profile("dev")
public class SetupDataLoader {
  private boolean firstCall = false;
  private final ImageServiceClient imageServiceClient;
  private final ProductMapper.Startup.Common setupProductMapper;
  private final ProductRepository productRepository;
  private final Base64ViewService base64ViewService;

  @Autowired
  public SetupDataLoader(ImageServiceClient imageServiceClient,
                         ProductMapper.Startup.Common setupProductMapper,
                         ProductRepository productRepository,
                         Base64ViewService base64ViewService) {
    this.imageServiceClient = imageServiceClient;
    this.setupProductMapper = setupProductMapper;
    this.productRepository = productRepository;
    this.base64ViewService = base64ViewService;
  }

  @EventListener(ContextRefreshedEvent.class)
  public void onApplicationEvent() {
    if (!firstCall) {
      firstCall = true;
      setup();
    }
  }

  private void setup() {
    if (productRepository.count() == 0) {
      for (SetupProduct setupProduct : SetupProduct.values()) {
        List<String> imagesContent = Arrays.stream(readAllFilesBytes(setupProduct.getImagePaths()))
          .map(base64ViewService::view)
          .toList();
        List<Long> imagesId = imageServiceClient.saveAll(imagesContent);
        Product product = setupProductMapper.toDomain(setupProduct, imagesId);
        productRepository.save(product);
      }
    }
  }

  private byte[][] readAllFilesBytes(List<String> paths) {
    byte[][] allBytes = new byte[paths.size()][];
    for (int i = 0; i < allBytes.length; i++) {
      try (InputStream resourceAsStream = getClass().getResourceAsStream(paths.get(i))) {
        Objects.requireNonNull(resourceAsStream);
        allBytes[i] = resourceAsStream.readAllBytes();
      } catch (IOException exception) {
        throw new RuntimeException(exception);
      }
    }
    return allBytes;
  }
}
