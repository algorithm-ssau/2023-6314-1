package com.team.notificationservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.notificationservice.dto.ActivationDto;
import com.team.notificationservice.infrastructure.mail.message.builder.MessageBuilder;
import com.team.notificationservice.infrastructure.mail.message.mapper.SimpleActivationLinkWrapper;
import com.team.notificationservice.service.contract.MailSendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivationMessageHandler {
  private final ObjectMapper objectMapper;
  private final SimpleActivationLinkWrapper linkWrapper;
  private final MailSendingService mailSendingService;
  private final MessageBuilder messageBuilder;

  @Autowired
  public ActivationMessageHandler(ObjectMapper objectMapper,
                                  SimpleActivationLinkWrapper linkWrapper,
                                  MailSendingService mailSendingService,
                                  @Qualifier("activationMessageBuilder") MessageBuilder messageBuilder) {
    this.objectMapper = objectMapper;
    this.linkWrapper = linkWrapper;
    this.mailSendingService = mailSendingService;
    this.messageBuilder = messageBuilder;
  }

  @KafkaListener(topics = "t.activation.link")
  public void handleActivationMessage(@Payload String message) throws JsonProcessingException {
    ActivationDto dto = objectMapper.readValue(message, ActivationDto.class);
    String text = messageBuilder.buildActivationMessage(dto);
    SimpleMailMessage preparedMailMessage = linkWrapper.toActivationMessage("Activation", dto, text);
    mailSendingService.send(preparedMailMessage);
  }

  @KafkaListener(topics = "t.activation.update")
  public void handleUpdateMessage(@Payload String message) throws JsonProcessingException {
    ActivationDto dto = objectMapper.readValue(message, ActivationDto.class);
    String text = messageBuilder.buildUpdateMessage(dto);
    SimpleMailMessage preparedMailMessage = linkWrapper.toActivationMessage("Update Email", dto, text);
    mailSendingService.send(preparedMailMessage);
  }
}
