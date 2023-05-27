package com.team.orderservice.model.exception;

import com.team.base.view.exception.NotFoundException;

public class StatusNotFoundException extends NotFoundException {
  public StatusNotFoundException() {
    super();
  }

  public StatusNotFoundException(String message) {
    super(message);
  }
}
