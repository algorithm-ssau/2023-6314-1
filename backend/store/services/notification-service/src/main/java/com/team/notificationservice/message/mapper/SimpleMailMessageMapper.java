package com.team.notificationservice.message.mapper;

import com.team.notificationservice.dto.ActivationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:mail.properties")
public class SimpleMailMessageMapper {
  @Value("${mail.username}")
  private String from;

  public SimpleMailMessage toMailMessage(ActivationDto activationDto) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(activationDto.getEmail());
    message.setSubject("Activation");
    message.setText(buildMessageText(activationDto));
    return message;
  }

  private String buildMessageText(ActivationDto activationDto) {
    return "Hello " + activationDto.getName() + ',' + "\n\n" +
      "Thank you for using our service. Your account has been successfully registered." + "\n\n" +
      "Please click here to verify your account:" + "\n\n" +
      activationDto.getActivationLink() + "\n\n" +
      "This link will expire in 24 hours!" + "\n\n" +
      "Kind regards," + "\n\n" +
      "Your com.team.store";
  }
}
