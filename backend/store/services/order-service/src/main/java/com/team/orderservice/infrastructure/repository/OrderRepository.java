package com.team.orderservice.infrastructure.repository;

import com.team.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByUserId(Long userId);
  void deleteAllByUserId(Long userId);
}
