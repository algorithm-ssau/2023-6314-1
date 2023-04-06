package com.team.notificationservice.service.api;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public abstract class AbstractMailSendingService implements MailSendingService {
  private final JavaMailSender mailSender;

  public AbstractMailSendingService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void send(SimpleMailMessage mailMessage) {
    mailSender.send(mailMessage);
  }
}
