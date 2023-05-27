package com.team.userservice.service.exception;

public class UrlMatchingException extends RuntimeException {
  public UrlMatchingException() {
    super();
  }

  public UrlMatchingException(String message) {
    super(message);
  }
}
