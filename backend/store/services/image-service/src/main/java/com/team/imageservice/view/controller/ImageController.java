package com.team.imageservice.view.controller;

import com.team.imageservice.model.Image;
import com.team.imageservice.service.contract.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
  private final ImageService imageService;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody String get(@PathVariable Long id) {
    Image presentImage = imageService.getById(id);
    return presentImage.getContent();
  }

  @PostMapping
  public ResponseEntity<Long> save(@RequestBody String content) {
    var image = new Image(content);
    Long indexOfSavedImage = imageService.save(image);
    return ResponseEntity.ok().body(indexOfSavedImage);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    imageService.delete(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> update(@PathVariable Long id,
                                  @RequestBody String content) {
    Image updated = imageService.update(id, new Image(content));
    return ResponseEntity.ok(updated.getContent());
  }
}
