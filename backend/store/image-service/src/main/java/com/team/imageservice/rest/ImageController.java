package com.team.imageservice.rest;

import com.team.imageservice.data.Image;
import com.team.imageservice.dto.ImageRequestDto;
import com.team.imageservice.dto.ImageResponseDto;
import com.team.imageservice.service.ImageDeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
  private final ImageDeliveryService imageDeliveryService;

  @GetMapping("/{id}")
  public ResponseEntity<ImageResponseDto> get(@PathVariable Long id) {
    Image presentImage = imageDeliveryService.getById(id);
    return ResponseEntity.ok().body(ImageResponseDto.from(presentImage));
  }

  @PostMapping
  public ResponseEntity<Long> save(@Valid @RequestBody ImageRequestDto imageRequestDto) {
    Long indexOfSavedImage = imageDeliveryService.save(imageRequestDto.toImage());
    return ResponseEntity.ok().body(indexOfSavedImage);
  }
}
