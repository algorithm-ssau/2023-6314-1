package com.team.orderservice.model.exception;

public class StatusNotFoundException extends RuntimeException {
  public StatusNotFoundException() {
    super();
  }

  public StatusNotFoundException(String message) {
    super(message);
  }
}
