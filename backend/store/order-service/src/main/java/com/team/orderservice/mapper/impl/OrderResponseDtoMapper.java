package com.team.orderservice.mapper.impl;

import com.team.orderservice.data.Order;
import com.team.orderservice.dto.OrderResponseDto;
import com.team.orderservice.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderResponseDtoMapper implements ObjectMapper<Order, OrderResponseDto> {
  private final AddressResponseDtoMapper addressResponseDtoMapper;
  private final StatusResponseDtoMapper statusResponseDtoMapper;

  @Override
  public OrderResponseDto map(Order from) {
    return new OrderResponseDto(
      from.getId(),
      addressResponseDtoMapper.map(from.getArrivalAddress()),
      from.getProducts(),
      from.getPayloadDateTime(),
      from.getArrivalDateTime(),
      statusResponseDtoMapper.map(from.getStatus())
    );
  }
}
