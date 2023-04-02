package com.team.userservice.exception;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException() {
    super();
  }

  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
