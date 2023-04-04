package com.team.imageservice.service;

import com.team.imageservice.data.Image;
import com.team.imageservice.exception.ImageNotFoundException;
import com.team.imageservice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonImageDeliveryService implements ImageDeliveryService {
  private final ImageRepository imageRepository;

  public Image getById(Long id) {
    return imageRepository.findById(id)
      .orElseThrow(() -> new ImageNotFoundException("Image with id: " + id + " not found"));
  }

  public Long save(Image image) {
    imageRepository.save(image);
    return image.getId();
  }

  @Override
  public void delete(Long id) {
    imageRepository.deleteById(id);
  }
}
