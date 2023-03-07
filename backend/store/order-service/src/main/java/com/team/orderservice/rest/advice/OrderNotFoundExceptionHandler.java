package com.team.orderservice.rest.advice;

import com.team.orderservice.exception.OrderNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderNotFoundExceptionHandler {

  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<String> handle(OrderNotFoundException e) {
    e.printStackTrace();
    return ResponseEntity.status(404).body(e.getMessage());
  }

}
