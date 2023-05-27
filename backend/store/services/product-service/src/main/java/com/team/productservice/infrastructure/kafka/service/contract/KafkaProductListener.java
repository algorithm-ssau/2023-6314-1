package com.team.productservice.infrastructure.kafka.service.contract;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.messaging.handler.annotation.Payload;

public interface KafkaProductListener {
  void handleActivationMessage(@Payload String message) throws JsonProcessingException;
}
