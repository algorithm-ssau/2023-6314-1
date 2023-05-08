package com.team.userservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.userservice.model.User;
import com.team.userservice.dto.UserDto;
import com.team.userservice.mapper.UserMapper;
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
  private final ObjectMapper objectMapper;
  private final UserMapper.Response.Activation activationDtoMapper;

  @Autowired
  public ActivationSender(KafkaTemplate<String, String> kafkaTemplate,
                          ObjectMapper objectMapper,
                          UserMapper.Response.Activation activationDtoMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
    this.activationDtoMapper = activationDtoMapper;
  }

  public void sendActivation(User user, String activationLink) {
    try {
      UserDto.Response.Activation dto = activationDtoMapper.toDto(user, activationLink);
      String message = objectMapper.writeValueAsString(dto);
      kafkaTemplate.send(activationTopic, message);
      log.info("SEND activation with name: {} email: {}, created date: {}",
        dto.getName(), dto.getEmail(), dto.getCreated());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
