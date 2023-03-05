package com.team.userservice.mapper;

public interface ObjectMapper<F, T> {
  T map(F from);
}
