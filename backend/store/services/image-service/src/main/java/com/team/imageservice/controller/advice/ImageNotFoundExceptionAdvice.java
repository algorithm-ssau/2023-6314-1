package com.team.imageservice.controller.advice;

import com.team.imageservice.model.exception.ImageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ImageNotFoundExceptionAdvice {
  @ExceptionHandler(ImageNotFoundException.class)
  public ResponseEntity<?> handle(ImageNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
