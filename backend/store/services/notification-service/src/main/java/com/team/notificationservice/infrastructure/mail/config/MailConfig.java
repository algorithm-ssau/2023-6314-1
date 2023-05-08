package com.team.notificationservice.infrastructure.mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {
  @Value("${mail.username}")
  private String mailUsername;

  @Value("${mail.password}")
  private String mailPassword;

  @Bean
  public JavaMailSender mailRuSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.mail.ru");
    mailSender.setPort(587);
    mailSender.setUsername(mailUsername);
    mailSender.setPassword(mailPassword);

    Properties properties = mailSender.getJavaMailProperties();
    properties.put("mail.smtp.auth", true);
    properties.put("mail.smtp.starttls.enable", true);
    return mailSender;
  }
}
