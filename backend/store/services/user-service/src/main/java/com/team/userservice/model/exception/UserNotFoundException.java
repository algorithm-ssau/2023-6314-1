package com.team.userservice.model.exception;

import com.team.base.view.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException() {
    super();
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}
