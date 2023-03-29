package com.team.jwtspringbootstarter.jwt.exception;

public class TokenResolvingException extends RuntimeException {
  public TokenResolvingException() {
    super();
  }

  public TokenResolvingException(String message) {
    super(message);
  }
}
