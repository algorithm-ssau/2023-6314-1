package com.team.imageservice.service.impl;

import com.team.imageservice.model.Image;
import com.team.imageservice.model.exception.ImageNotFoundException;
import com.team.imageservice.infrastructure.repository.ImageRepository;
import com.team.imageservice.service.contract.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonImageService implements ImageService {
  private final ImageRepository imageRepository;

  @Autowired
  public CommonImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public Image getById(Long id) {
    return imageRepository.findById(id)
      .orElseThrow(() -> new ImageNotFoundException("Image with id: " + id + " not found"));
  }

  @Override
  public Long save(Image image) {
    imageRepository.save(image);
    return image.getId();
  }

  @Override
  public void delete(Long id) {
    imageRepository.deleteById(id);
  }

  @Override
  public Image update(Long id, Image update) {
    Image image = getById(id);
    image.setContent(update.getContent());
    return imageRepository.save(image);
  }
}
