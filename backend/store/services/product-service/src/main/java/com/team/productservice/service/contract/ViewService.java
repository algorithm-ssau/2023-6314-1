package com.team.productservice.service.contract;

public interface ViewService<T> {
  T view(byte[] content);
}
