package com.team.orderservice.service.contract;

import com.team.orderservice.model.Order;

import java.util.List;

public interface OrderService {
  boolean isPresent(Long id);
  List<Order> getAll();
  void create(Order order);
  void update(Order order);
  List<Order> getAllByUserId(Long userId);
  Order getById(Long id);
  void cancelById(Long id);
  void cancelAllByUserId(Long userId);
}
