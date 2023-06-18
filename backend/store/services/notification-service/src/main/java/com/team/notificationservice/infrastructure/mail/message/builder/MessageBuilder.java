package com.team.notificationservice.infrastructure.mail.message.builder;

import com.team.notificationservice.dto.ActivationDto;

public interface MessageBuilder {
  String buildActivationMessage(ActivationDto activationDto);
  String buildUpdateMessage(ActivationDto activationDto);
}
