package com.team.orderservice.service.contract;

import com.team.orderservice.model.Order;

import java.util.List;

public interface OrderService {
  List<Order> getAll();
  void create(Order order);
  List<Order> getAllByUserId(Long userId);
  Order getById(Long id);
  void deleteById(Long id);
  void deleteAllByUserId(Long userID);
}
