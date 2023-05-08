package com.team.imageservice.controller;

import com.team.imageservice.model.Image;
import com.team.imageservice.service.contract.ImageDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
  private final ImageDeliveryService imageDeliveryService;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody String get(@PathVariable Long id) {
    Image presentImage = imageDeliveryService.getById(id);
    return presentImage.getContent();
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
