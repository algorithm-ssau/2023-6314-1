package com.team.notificationservice.infrastructure.mail.message.builder;

import com.team.notificationservice.dto.ActivationDto;
import org.springframework.stereotype.Component;

@Component
public class ActivationMessageBuilder implements MessageBuilder {
  @Override
  public String buildActivationMessage(ActivationDto activationDto) {
    return "Hello " + activationDto.getName() + ',' + "\n\n" +
      "Thank you for using our service. Your account has been successfully registered." + "\n\n" +
      "Please click here to verify your account:" + "\n\n" +
      activationDto.getActivationLink() + "\n\n" +
      "This link will expire in 24 hours!" + "\n\n" +
      "Kind regards," + "\n\n" +
      "Your Mango.";
  }

  @Override
  public String buildUpdateMessage(ActivationDto activationDto) {
    return "Hello " + activationDto.getName() + ',' + "\n\n" +
      "Thank you for using our service. Your email has been updated." + "\n\n" +
      "Please click here to verify your account:" + "\n\n" +
      activationDto.getActivationLink() + "\n\n" +
      "This link will expire in 24 hours!" + "\n\n" +
      "Kind regards," + "\n\n" +
      "Your Mango.";
  }
}
