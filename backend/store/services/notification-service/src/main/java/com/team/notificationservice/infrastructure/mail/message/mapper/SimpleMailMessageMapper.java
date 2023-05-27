package com.team.notificationservice.infrastructure.mail.message.mapper;

import com.team.notificationservice.dto.ActivationDto;
import com.team.notificationservice.infrastructure.mail.message.builder.MessageBuilderStrategy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:mail.properties")
@Getter
@Setter
public class SimpleMailMessageMapper {

  @Value("${mail.username}")
  private String from;

  private MessageBuilderStrategy messageBuilderStrategy;

  @Autowired
  public SimpleMailMessageMapper(@Qualifier("commonMessageBuilderStrategy") MessageBuilderStrategy defaultStrategy) {
    this.messageBuilderStrategy = defaultStrategy;
  }

  public SimpleMailMessage toMailMessage(ActivationDto activationDto) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(activationDto.getEmail());
    message.setSubject("Activation");
    message.setText(messageBuilderStrategy.build(activationDto));
    return message;
  }
}
