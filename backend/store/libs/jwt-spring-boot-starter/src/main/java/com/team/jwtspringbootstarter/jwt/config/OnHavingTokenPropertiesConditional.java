package com.team.jwtspringbootstarter.jwt.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnHavingTokenPropertiesConditional implements Condition {
  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    var environment = context.getEnvironment();
    var accessTokenSecret = environment.getProperty("jwt.token.access.secret");
    var expired = environment.getProperty("jwt.token.access.expired");
    return accessTokenSecret != null && expired != null;
  }
}
