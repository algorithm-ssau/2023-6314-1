package com.team.orderservice.infrastructure.kafka.service.contract;

import com.team.orderservice.infrastructure.kafka.dto.ProductQuantityDto;

public interface KafkaMessageSender {
  void send(ProductQuantityDto productQuantityDto);
}
