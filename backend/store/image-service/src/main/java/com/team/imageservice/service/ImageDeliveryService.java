package com.team.imageservice.service;

import com.team.imageservice.data.Image;
import com.team.imageservice.exception.ImageNotFoundException;

public interface ImageDeliveryService {
  Image getById(Long id);
  Long save(Image image);
}
