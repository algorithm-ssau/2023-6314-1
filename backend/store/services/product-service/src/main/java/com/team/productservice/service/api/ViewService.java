package com.team.productservice.service.api;

public interface ViewService<T> {
  T view(byte[] content);
}
