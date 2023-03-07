package com.team.productservice.service.mapper;

public interface ObjectMapper<F, T> {
  T map(F from);
}
