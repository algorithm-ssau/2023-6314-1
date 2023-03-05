package com.team.orderservice.repository;

import com.team.orderservice.data.Order;

public interface OrderRepository {
  Order save(Order order);
  Long count();
}
