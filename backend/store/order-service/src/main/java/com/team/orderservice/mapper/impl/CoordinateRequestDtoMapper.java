package com.team.orderservice.mapper.impl;

import com.team.orderservice.data.Coordinate;
import com.team.orderservice.dto.CoordinateRequestDto;
import com.team.orderservice.mapper.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CoordinateRequestDtoMapper implements ObjectMapper<CoordinateRequestDto, Coordinate> {
  @Override
  public Coordinate map(CoordinateRequestDto from) {
    return new Coordinate(from.getLatitude(), from.getLongitude());
  }
}
