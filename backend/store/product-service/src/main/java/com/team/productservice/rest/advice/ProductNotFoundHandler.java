package com.team.productservice.rest.advice;

import com.team.productservice.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductNotFoundHandler {
  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<String> handle(ProductNotFoundException exception) {
    return ResponseEntity.status(404).body(exception.getMessage());
  }
}
