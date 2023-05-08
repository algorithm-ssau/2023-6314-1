package com.team.notificationservice.infrastructure.mail.message.builder;

import com.team.notificationservice.dto.ActivationDto;

public interface MessageBuilderStrategy {
  String build(ActivationDto activationDto);
}
