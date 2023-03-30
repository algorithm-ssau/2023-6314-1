package com.team.imageservice.exception;

public class ImageNotFoundException extends RuntimeException {
  public ImageNotFoundException() {
    super();
  }

  public ImageNotFoundException(String message) {
    super(message);
  }
}
