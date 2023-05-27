package com.team.notificationservice.service.contract;

import org.springframework.mail.SimpleMailMessage;

public interface MailSendingService {
  void send(SimpleMailMessage mailMessage);
}
