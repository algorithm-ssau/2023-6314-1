package com.team.notificationservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.notificationservice.dto.ActivationDto;
import com.team.notificationservice.infrastructure.mail.message.mapper.SimpleMailMessageMapper;
import com.team.notificationservice.service.contract.MailSendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivationMessageHandler {
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

  @KafkaListener(topics = "t.activation.link")
  public void handleActivationMessage(@Payload String message) throws JsonProcessingException {
    ActivationDto activationDto = objectMapper.readValue(message, ActivationDto.class);
    log.info("KAFKA listen: for name: {}, email: {}, created: {} from topic: t.activation.link",
      activationDto.getName(), activationDto.getEmail(), activationDto.getCreated());
    mailSendingService.send(simpleMailMessageMapper.toMailMessage(activationDto));
  }
}
