package com.team.userservice.validation;

import com.team.userservice.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.OffsetDateTime;

public class UpdateUserValidator implements ConstraintValidator<ValidUpdateUser, User> {
  @Override
  public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
    Long id = user.getId();
    String email = user.getEmail();
    Boolean active = user.getActive();
    OffsetDateTime created = user.getCreated();
    OffsetDateTime updated = user.getUpdated();
    return id == null && email == null && active == null && created == null && updated == null;
  }
}
