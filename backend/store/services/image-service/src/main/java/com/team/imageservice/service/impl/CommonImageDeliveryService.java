package com.team.imageservice.service.impl;

import com.team.imageservice.data.Image;
import com.team.imageservice.exception.ImageNotFoundException;
import com.team.imageservice.repository.ImageRepository;
import com.team.imageservice.service.api.ImageDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonImageDeliveryService implements ImageDeliveryService {
  private final ImageRepository imageRepository;


  @Autowired
  public CommonImageDeliveryService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public Image getById(Long id) {
    return imageRepository.findById(id)
      .orElseThrow(() -> new ImageNotFoundException("Image with id: " + id + " not found"));
  }

  public Long save(Image image) {
    imageRepository.save(image);
    return image.getId();
  }
}
