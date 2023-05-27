package com.team.orderservice.model.exception;

import com.team.base.view.exception.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
  public OrderNotFoundException() {
    super();
  }

  public OrderNotFoundException(String message) {
    super(message);
  }
}
