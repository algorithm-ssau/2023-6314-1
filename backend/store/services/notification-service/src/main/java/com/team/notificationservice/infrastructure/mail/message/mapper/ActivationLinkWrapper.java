package com.team.notificationservice.infrastructure.mail.message.mapper;

import com.team.notificationservice.dto.IdentifyByEmail;
import org.springframework.mail.MailMessage;

public interface ActivationLinkWrapper {
  MailMessage toActivationMessage(String subject, IdentifyByEmail identifyByEmail, String text);
}
