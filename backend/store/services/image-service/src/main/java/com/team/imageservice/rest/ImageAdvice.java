package com.team.imageservice.rest;

import com.team.imageservice.exception.ImageException;
import com.team.imageservice.exception.ImageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ImageAdvice {
  @ExceptionHandler(ImageNotFoundException.class)
  public ResponseEntity<String> handle(ImageNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }
}
