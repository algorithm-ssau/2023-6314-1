package com.team.notificationservice.service.impl;

import com.team.notificationservice.service.api.AbstractMailSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailRuSendingService extends AbstractMailSendingService {
  @Autowired
  public MailRuSendingService(@Qualifier("mailRuSender") JavaMailSender mailRuSender) {
    super(mailRuSender);
  }
}
