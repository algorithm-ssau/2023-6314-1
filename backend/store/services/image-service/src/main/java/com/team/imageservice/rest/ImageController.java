package com.team.imageservice.rest;

import com.team.imageservice.data.Image;
import com.team.imageservice.mapper.ImageMapper;
import com.team.imageservice.service.api.ImageDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
  private final ImageDeliveryService imageDeliveryService;
  private final ImageMapper.Response.Common commonImageResponseMapper;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody String get(@PathVariable Long id) {
    Image presentImage = imageDeliveryService.getById(id);
    var commonImageResponse = commonImageResponseMapper.toDto(presentImage);
    return commonImageResponse.getContent();
  }

  @PostMapping
  public ResponseEntity<Long> save(@RequestBody String content) {
    var image = new Image(content);
    Long indexOfSavedImage = imageDeliveryService.save(image);
    return ResponseEntity.ok().body(indexOfSavedImage);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    imageDeliveryService.delete(id);
    return ResponseEntity.ok().build();
  }
}
