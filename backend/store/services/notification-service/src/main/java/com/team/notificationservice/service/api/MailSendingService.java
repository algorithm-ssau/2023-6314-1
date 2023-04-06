package com.team.notificationservice.service.api;

import org.springframework.mail.SimpleMailMessage;

public interface MailSendingService {
  void send(SimpleMailMessage mailMessage);
}
