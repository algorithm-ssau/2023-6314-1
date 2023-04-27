package com.team.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggerAspectConfig {
  @Bean
  public LoggingAspect loggingAspect() {
    return new LoggingAspect();
  }
}
