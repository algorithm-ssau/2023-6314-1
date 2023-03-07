package com.team.orderservice.mapper.impl;

import com.team.orderservice.data.Coordinate;
import com.team.orderservice.dto.CoordinateResponseDto;
import com.team.orderservice.mapper.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CoordinateResponseDtoMapper implements ObjectMapper<Coordinate, CoordinateResponseDto> {
  @Override
  public CoordinateResponseDto map(Coordinate from) {
    return new CoordinateResponseDto(from.getLatitude(), from.getLongitude());
  }
}
