package com.team.notificationservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.notificationservice.dto.ActivationDto;
import com.team.notificationservice.mapper.SimpleMailMessageMapper;
import com.team.notificationservice.service.api.MailSendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivationMessageHandler {
  private static final String activationTopic = "${topic.name}";
  private final ObjectMapper objectMapper;
  private final SimpleMailMessageMapper simpleMailMessageMapper;
  private final MailSendingService mailSendingService;

  @Autowired
  public ActivationMessageHandler(ObjectMapper objectMapper,
                                  SimpleMailMessageMapper simpleMailMessageMapper,
                                  MailSendingService mailSendingService) {
    this.objectMapper = objectMapper;
    this.simpleMailMessageMapper = simpleMailMessageMapper;
    this.mailSendingService = mailSendingService;
  }

  @KafkaListener(topics = activationTopic)
  public void handleActivationMessage(String message) throws JsonProcessingException {
    log.info("Message handled: {}", message);
    ActivationDto activationDto = objectMapper.readValue(message, ActivationDto.class);
    mailSendingService.send(simpleMailMessageMapper.toMailMessage(activationDto));
  }
}
