package com.team.imageservice.rest;

import com.team.imageservice.data.Image;
import com.team.imageservice.dto.ImageDto;
import com.team.imageservice.mapper.ImageMapper;
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
  private final ImageMapper.Request.Common commonImageRequestMapper;
  private final ImageMapper.Response.Common commonImageResponseMapper;

  @GetMapping("/{id}")
  public ResponseEntity<ImageDto.Response.Common> get(@PathVariable Long id) {
    Image presentImage = imageDeliveryService.getById(id);
    var commonImageResponse = commonImageResponseMapper.toDto(presentImage);
    return ResponseEntity.ok().body(commonImageResponse);
  }

  @PostMapping
  public ResponseEntity<Long> save(@Valid @RequestBody ImageDto.Request.Common imageRequestDto) {
    var image = commonImageRequestMapper.toDomain(imageRequestDto);
    Long indexOfSavedImage = imageDeliveryService.save(image);
    return ResponseEntity.ok().body(indexOfSavedImage);
  }
}
