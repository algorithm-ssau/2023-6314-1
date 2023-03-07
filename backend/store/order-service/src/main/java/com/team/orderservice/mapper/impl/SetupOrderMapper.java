package com.team.orderservice.mapper.impl;

import com.team.orderservice.data.Order;
import com.team.orderservice.mapper.ObjectMapper;
import com.team.orderservice.startup.SetupOrder;
import org.springframework.stereotype.Component;

@Component
public class SetupOrderMapper implements ObjectMapper<SetupOrder, Order> {
  @Override
  public Order map(SetupOrder from) {
    Order order = new Order();
    order.setArrivalAddress(from.getArrivalAddress());
    order.setProducts(from.getProducts());
    order.setUserId(from.getUserId());
    order.setPayloadDateTime(from.getPayloadDateTime());
    order.setArrivalDateTime(from.getArrivalDateTime());
    return order;
  }
}
