package com.team.imageservice.service.contract;

import com.team.imageservice.model.Image;

public interface ImageService {
  Image getById(Long id);
  Long save(Image image);
  void delete(Long id);
  Image update(Long id, Image update);
}
