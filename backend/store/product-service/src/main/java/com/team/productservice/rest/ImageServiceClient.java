package com.team.productservice.rest;

import com.team.productservice.rest.dto.ImageRequestDto;
import com.team.productservice.rest.dto.ImageResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(
  value = "image-service",
  fallbackFactory = ImageServiceClient.ImageServiceFeignClientFallbackFactory.class
)
public interface ImageServiceClient {
  @RequestMapping(value = "api/images/{id}", method = RequestMethod.GET)
  ResponseEntity<ImageResponseDto> get(@PathVariable Long id);

  @RequestMapping(value = "api/images", method = RequestMethod.POST)
  ResponseEntity<Long> save(@Valid @RequestBody ImageRequestDto imageRequestDto);

  @Component
  @Slf4j
  class ImageServiceFeignClientFallbackFactory implements FallbackFactory<ImageServiceClient> {
    @Override
    public ImageServiceClient create(Throwable cause) {
      return new ImageServiceClient() {
        @Override
        public ResponseEntity<ImageResponseDto> get(Long id) {
          log.error("Cannot find image with id = {}", id);
          throw new IllegalArgumentException(cause);
        }

        @Override
        public ResponseEntity<Long> save(ImageRequestDto imageRequestDto) {
          log.error("Cannot save image = {}", imageRequestDto);
          throw new IllegalArgumentException(cause);
        }
      };
    }
  }
}