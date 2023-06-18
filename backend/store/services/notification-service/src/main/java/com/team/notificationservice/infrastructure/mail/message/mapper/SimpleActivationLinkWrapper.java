package com.team.notificationservice.infrastructure.mail.message.mapper;

import com.team.notificationservice.dto.IdentifyByEmail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:mail.properties")
@Getter
@Setter
public class SimpleActivationLinkWrapper implements ActivationLinkWrapper {
  @Value("${mail.username}")
  private String from;

  @Override
  public SimpleMailMessage toActivationMessage(String subject, IdentifyByEmail identifyByEmail, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(identifyByEmail.getEmail());
    message.setSubject(subject);
    message.setText(text);
    return message;
  }
}
