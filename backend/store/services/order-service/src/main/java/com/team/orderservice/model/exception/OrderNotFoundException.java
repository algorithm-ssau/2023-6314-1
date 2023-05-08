package com.team.orderservice.model.exception;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException() {
    super();
  }

  public OrderNotFoundException(String message) {
    super(message);
  }
}
