package com.team.notificationservice.config;

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

  @Value("${mail.transport.protocol}")
  private String transportProtocol;

  @Value("${mail.smtp.auth}")
  private boolean auth;

  @Value("${mail.smtp.starttls.enable}")
  private boolean startTls;

  @Value("${mail.debug}")
  private boolean debugMode;

  @Bean
  public JavaMailSender mailRuSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.mail.ru");
    mailSender.setPort(587);
    mailSender.setUsername(mailUsername);
    mailSender.setPassword(mailPassword);

    Properties properties = mailSender.getJavaMailProperties();
    properties.put("mail.transport.protocol", transportProtocol);
    properties.put("mail.smtp.auth", auth);
    properties.put("mail.smtp.starttls.enable", startTls);
    properties.put("mail.debug", debugMode);
    return mailSender;
  }
}
