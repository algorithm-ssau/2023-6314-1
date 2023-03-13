package com.team.orderservice.mapper.impl;

import com.team.orderservice.data.Order;
import com.team.orderservice.dto.OrderRequestDto;
import com.team.orderservice.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRequestDtoMapper implements ObjectMapper<OrderRequestDto, Order> {
  private final AddressRequestDtoMapper addressRequestDtoMapper;
  private final StatusRequestDtoMapper statusRequestDtoMapper;

  @Override
  public Order map(OrderRequestDto from) {
    return new Order(
      addressRequestDtoMapper.map(from.getArrivalAddress()),
      from.getProducts(),
      from.getUserId(),
      from.getArrivalDateTime()
    );
  }
}
