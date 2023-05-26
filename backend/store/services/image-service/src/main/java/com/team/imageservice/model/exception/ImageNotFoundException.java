package com.team.imageservice.model.exception;

import com.team.base.view.exception.NotFoundException;

public class ImageNotFoundException extends NotFoundException {
  public ImageNotFoundException() {
    super();
  }

  public ImageNotFoundException(String message) {
    super(message);
  }
}
