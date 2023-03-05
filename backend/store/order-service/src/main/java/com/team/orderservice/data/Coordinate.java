package com.team.orderservice.data;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Coordinate {
  private final Double latitude;
  private final Double longitude;
}
