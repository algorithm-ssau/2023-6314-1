package com.team.orderservice.mapper.impl;

import com.team.orderservice.data.Status;
import com.team.orderservice.dto.StatusDto;
import com.team.orderservice.mapper.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class StatusResponseDtoMapper implements ObjectMapper<Status, StatusDto> {
  @Override
  public StatusDto map(Status from) {
    return new StatusDto(from.getName());
  }
}
