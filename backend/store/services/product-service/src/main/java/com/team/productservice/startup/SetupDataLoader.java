package com.team.productservice.startup;

import com.team.productservice.data.Product;
import com.team.productservice.mapper.ProductMapper;
import com.team.productservice.repository.ProductRepository;
import com.team.productservice.rest.client.ImageServiceClient;
import com.team.productservice.rest.client.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class SetupDataLoader {
  private boolean firstCall = false;
  private final ImageServiceClient imageServiceClient;
  private final ProductMapper.Startup.Common setupProductMapper;
  private final ProductRepository productRepository;

  @EventListener(ContextRefreshedEvent.class)
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (!firstCall) {
      firstCall = true;
      setup();
    }
  }

  private void setup() {
    if (productRepository.count() == 0) {
      for (SetupProduct setupProduct : SetupProduct.values()) {
        byte[][] allFilesBytes = readAllFilesBytes(setupProduct.getImagePaths());
        List<ImageDto.Request.Common> imageRequestDtos = Arrays.stream(allFilesBytes)
          .map(ImageDto.Request.Common::new)
          .toList();
        List<Long> imagesId = imageServiceClient.saveAll(imageRequestDtos);
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
        BufferedImage image = ImageIO.read(resourceAsStream);
        allBytes[i] = ((DataBufferByte) image.getData().getDataBuffer()).getData();
      } catch (IOException exception) {
        throw new RuntimeException(exception);
      }
    }
    return allBytes;
  }
}
