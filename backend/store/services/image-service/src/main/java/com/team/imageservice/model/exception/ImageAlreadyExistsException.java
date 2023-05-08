package com.team.imageservice.model.exception;

public class ImageAlreadyExistsException extends RuntimeException {
  public ImageAlreadyExistsException(String s) {
    super(s);
  }
}
