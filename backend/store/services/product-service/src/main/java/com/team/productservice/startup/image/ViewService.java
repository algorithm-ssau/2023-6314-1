package com.team.productservice.startup.image;

public interface ViewService<T> {
  T view(byte[] content);
}
