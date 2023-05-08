package com.team.orderservice.controller.advice;

import com.team.orderservice.model.exception.OrderNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<String> handle(OrderNotFoundException e) {
    e.printStackTrace();
    return ResponseEntity.status(404).body(e.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handle(ConstraintViolationException e) {
    e.printStackTrace();
    return ResponseEntity.status(400).body(e.getMessage());
  }
}
