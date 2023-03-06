package com.team.userservice.rest.advice;

import com.team.userservice.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserNotFoundExceptionAdvice {
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleException(UserNotFoundException ex) {
    return ResponseEntity.status(404).body(ex.getMessage());
  }
}
