package com.team.productservice.service.contract;

public interface ProductKafkaUpdater {
  void updateQuantity(Long id, Long delta);
}
