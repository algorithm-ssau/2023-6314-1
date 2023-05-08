package com.team.productservice.model.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException() {
    super();
  }

  public ProductNotFoundException(String message) {
    super(message);
  }
}
