package com.team.imageservice.service.api;

import com.team.imageservice.data.Image;

public interface ImageDeliveryService {
  Image getById(Long id);
  Long save(Image image);
  void delete(Long id);
}
