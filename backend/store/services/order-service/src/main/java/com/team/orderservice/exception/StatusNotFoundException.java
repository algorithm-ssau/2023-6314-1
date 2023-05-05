package com.team.orderservice.exception;

public class StatusNotFoundException extends RuntimeException {
  public StatusNotFoundException() {
    super();
  }

  public StatusNotFoundException(String message) {
    super(message);
  }
}
