package com.team.imageservice.model.exception;

public class ImageAlreadyExistsException extends IllegalArgumentException {
  public ImageAlreadyExistsException(String s) {
    super(s);
  }
}
