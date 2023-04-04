package com.team.imageservice.rest;

import com.team.imageservice.data.Image;
import com.team.imageservice.dto.ImageDto;
import com.team.imageservice.mapper.ImageMapper;
import com.team.imageservice.service.ImageDeliveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {
  private final ImageDeliveryService imageDeliveryService;
  private final ImageMapper.Request.Common commonImageRequestMapper;
  private final ImageMapper.Response.Common commonImageResponseMapper;

  @Autowired
  public ImageController(ImageDeliveryService imageDeliveryService,
                         ImageMapper.Request.Common commonImageRequestMapper,
                         ImageMapper.Response.Common commonImageResponseMapper) {
    this.imageDeliveryService = imageDeliveryService;
    this.commonImageRequestMapper = commonImageRequestMapper;
    this.commonImageResponseMapper = commonImageResponseMapper;
  }

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
