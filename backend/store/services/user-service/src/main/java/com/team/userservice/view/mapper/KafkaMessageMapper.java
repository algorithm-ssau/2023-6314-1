package com.team.userservice.view.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.userservice.view.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageMapper {
  private final ObjectMapper objectMapper;

  @Autowired
  public KafkaMessageMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String toMessage(UserDto.Response.Activation dto) {
    try {
      return objectMapper.writeValueAsString(dto);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
