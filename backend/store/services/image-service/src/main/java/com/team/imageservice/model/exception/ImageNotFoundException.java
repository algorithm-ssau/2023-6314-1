package com.team.imageservice.model.exception;

public class ImageNotFoundException extends ImageException {
  public ImageNotFoundException() {
    super();
  }

  public ImageNotFoundException(String message) {
    super(message);
  }
}
