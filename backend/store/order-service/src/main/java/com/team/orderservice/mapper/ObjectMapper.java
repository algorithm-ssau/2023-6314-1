package com.team.orderservice.mapper;

public interface ObjectMapper<F, T> {
  T map(F from);
}
