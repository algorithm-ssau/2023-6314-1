package com.team.userservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.Valid;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpdateUserValidator.class)
@Documented
@Valid
public @interface ValidUpdateUser {
  String message() default "Invalid update user";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
