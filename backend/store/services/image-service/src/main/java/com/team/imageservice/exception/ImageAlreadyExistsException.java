package com.team.imageservice.exception;

public class ImageAlreadyExistsException extends RuntimeException {
  public ImageAlreadyExistsException(String s) {
    super(s);
  }
}
