package com.team.userservice.model.exception;

import com.team.base.view.exception.NotFoundException;

public class RoleNotFoundException extends NotFoundException {
  public RoleNotFoundException() {
    super();
  }

  public RoleNotFoundException(String message) {
    super(message);
  }
}
