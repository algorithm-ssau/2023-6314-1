package com.team.userservice.model.exception;

public class UserAlreadyExistsException extends IllegalArgumentException {
  public UserAlreadyExistsException() {
    super();
  }

  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
