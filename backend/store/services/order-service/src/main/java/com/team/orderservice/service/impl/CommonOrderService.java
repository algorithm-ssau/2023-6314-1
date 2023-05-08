package com.team.orderservice.service.impl;

import com.team.orderservice.model.Order;
import com.team.orderservice.model.exception.OrderNotFoundException;
import com.team.orderservice.infrastructure.repository.OrderRepository;
import com.team.orderservice.service.contract.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonOrderService implements OrderService {
  private final OrderRepository orderRepository;

  @Override
  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  @Override
  public void create(Order order) {
    orderRepository.save(order);
  }

  @Override
  public List<Order> getAllByUserId(Long userId) {
    return orderRepository.findAllByUserId(userId);
  }

  @Override
  public Order getById(Long id) {
    return orderRepository.findById(id)
      .orElseThrow(() -> new OrderNotFoundException("Order with id: " + id + " not found"));
  }

  @Override
  public void deleteById(Long id) {
    orderRepository.deleteById(id);
  }

  @Override
  public void deleteAllByUserId(Long userID) {
    orderRepository.deleteAllByUserId(userID);
  }
}
