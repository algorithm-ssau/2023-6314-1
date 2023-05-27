package com.team.userservice.infrastructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivationSender {
  @Value("${topic.name}")
  private String activationTopic;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public ActivationSender(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendActivation(String message) {
    kafkaTemplate.send(activationTopic, message);
    log.info("SEND activation with message: {}", message);
  }
}
