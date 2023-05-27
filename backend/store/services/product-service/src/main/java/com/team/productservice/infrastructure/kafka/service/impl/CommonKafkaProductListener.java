package com.team.productservice.infrastructure.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.productservice.infrastructure.kafka.dto.ProductDto;
import com.team.productservice.infrastructure.kafka.service.contract.KafkaProductListener;
import com.team.productservice.service.contract.ProductKafkaUpdater;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CommonKafkaProductListener implements KafkaProductListener {
  private final ObjectMapper objectMapper;
  private final ProductKafkaUpdater productKafkaUpdater;

  public CommonKafkaProductListener(ObjectMapper objectMapper,
                              ProductKafkaUpdater productKafkaUpdater) {
    this.objectMapper = objectMapper;
    this.productKafkaUpdater = productKafkaUpdater;
  }

  @Override
  @KafkaListener(topics = "t.product.quantity.update")
  public void handleActivationMessage(@Payload String message) throws JsonProcessingException {
    ProductDto dto = objectMapper.readValue(message, ProductDto.class);
    productKafkaUpdater.updateQuantity(dto.getId(), dto.getDelta());
  }
}
