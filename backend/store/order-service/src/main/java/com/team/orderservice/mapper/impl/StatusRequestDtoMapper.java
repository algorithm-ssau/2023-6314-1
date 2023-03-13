package com.team.orderservice.mapper.impl;

import com.team.orderservice.data.Status;
import com.team.orderservice.dto.StatusDto;
import com.team.orderservice.mapper.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class StatusRequestDtoMapper implements ObjectMapper<StatusDto, Status> {
  @Override
  public Status map(StatusDto from) {
    return Status.valueOf(from.getStatusName());
  }
}
