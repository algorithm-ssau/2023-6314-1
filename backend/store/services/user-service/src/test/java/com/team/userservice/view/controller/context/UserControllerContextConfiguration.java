package com.team.userservice.view.controller.context;

import com.team.jwt.service.JwtSecurityProvider;
import com.team.userservice.infrastructure.kafka.ActivationSender;
import com.team.userservice.service.contract.UserService;
import com.team.userservice.service.impl.TokenProvider;
import com.team.userservice.service.impl.UrlMatcher;
import com.team.userservice.view.MapperFacade;
import com.team.userservice.view.controller.data.TestDataGenerator;
import com.team.userservice.view.controller.mock.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TokenMetadataConfig.class)
public class UserControllerContextConfiguration {

  @Bean
  public TestDataGenerator testDataGenerator() {
    return new TestDataGenerator();
  }

  @Bean
  public UserServiceMocker userServiceMocker(UserService userService) {
    return new UserServiceMocker(userService);
  }

  @Bean
  public MapperFacadeMocker mapperFacadeMocker(MapperFacade mapperFacade) {
    return new MapperFacadeMocker(mapperFacade);
  }

  @Bean
  public UrlMatcherMocker urlMatcherMocker(UrlMatcher urlMatcher) {
    return new UrlMatcherMocker(urlMatcher);
  }

  @Bean
  public ActivationSenderMocker activationSenderMocker(ActivationSender activationSender) {
    return new ActivationSenderMocker(activationSender);
  }

  @Bean
  public JwtSecurityProviderMocker jwtSecurityProviderMocker(JwtSecurityProvider jwtSecurityProvider) {
    return new JwtSecurityProviderMocker(jwtSecurityProvider);
  }

  @Bean
  public TokenProviderMocker tokenProviderMocker(TokenProvider tokenProvider) {
    return new TokenProviderMocker(tokenProvider);
  }
}
