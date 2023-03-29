package com.team.jwtspringbootstarter.jwt.config;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Conditional({OnHavingTokenPropertiesConditional.class})
public @interface ConditionalOnHavingTokenProperties {}
