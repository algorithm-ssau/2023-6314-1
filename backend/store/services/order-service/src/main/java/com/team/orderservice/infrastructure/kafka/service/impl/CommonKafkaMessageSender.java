package com.team.orderservice.infrastructure.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.orderservice.infrastructure.kafka.dto.ProductQuantityDto;
import com.team.orderservice.infrastructure.kafka.service.contract.KafkaMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonKafkaMessageSender implements KafkaMessageSender {
  @Value("${topic.name}")
  private String quantityUpdateTopic;

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @Autowired
  public CommonKafkaMessageSender(KafkaTemplate<String, String> kafkaTemplate,
                                  ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  @Override
  public void send(ProductQuantityDto productQuantityDto) {
    try {
      String message = objectMapper.writeValueAsString(productQuantityDto);
      kafkaTemplate.send(quantityUpdateTopic, message);
      log.info("SEND quantity update with message: {}", message);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
