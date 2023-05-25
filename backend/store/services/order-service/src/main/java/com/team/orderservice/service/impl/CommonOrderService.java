package com.team.orderservice.service.impl;

import com.team.orderservice.infrastructure.external.ProductServiceClient;
import com.team.orderservice.infrastructure.kafka.dto.ProductQuantityDto;
import com.team.orderservice.infrastructure.kafka.service.contract.KafkaMessageSender;
import com.team.orderservice.infrastructure.repository.OrderRepository;
import com.team.orderservice.model.Order;
import com.team.orderservice.service.contract.OrderService;
import com.team.orderservice.view.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommonOrderService implements OrderService {
  private final OrderRepository orderRepository;
  private final KafkaMessageSender kafkaMessageSender;
  private final ProductServiceClient productServiceClient;

  @Override
  public boolean isPresent(Long id) {
    if (id == null) return false;
    return orderRepository.findById(id).isPresent();
  }

  @Override
  public void create(Order order) {
    Long presentId = order.getId();
    if (isPresent(presentId)) {
      String msg = "Order " + presentId + " already present. Cannot create";
      throw new IllegalArgumentException(msg);
    } else {
      order.getProducts().forEach(id -> {
        ProductDto.Response.Common dto = productServiceClient.get(id);
        if (dto.getCountInStock() > 0) {
          kafkaMessageSender.send(new ProductQuantityDto(id, -1L));
        } else {
          String msg = "Cannot create order of product " + dto + ". 0 count in stock";
          throw new IllegalArgumentException(msg);
        }
      });
      orderRepository.save(order);
    }
  }

  @Override
  public void update(Order order) {
    Long presentId = order.getId();
    if (isPresent(presentId)) {
      orderRepository.save(order);
    } else {
      String msg = "Order " + presentId + " already present. Cannot create";
      throw new IllegalArgumentException(msg);
    }
  }

  @Override
  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  @Override
  public List<Order> getAllByUserId(Long userId) {
    return orderRepository.findAllByUserId(userId);
  }

  @Override
  public Order getById(Long id) {
    String msg = "Order with id: " + id + " not found";
    return orderRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException(msg));
  }

  @Override
  public void cancelById(Long id) {
    Order order = getById(id);
    order.getProducts().forEach(productId -> {
      kafkaMessageSender.send(new ProductQuantityDto(productId, 1L));
    });
    orderRepository.deleteById(id);
  }

  @Override
  public void cancelAllByUserId(Long userId) {
    List<Order> orders = orderRepository.findAllByUserId(userId);
    orders.forEach(order -> {
      order.getProducts().forEach(productId -> {
        kafkaMessageSender.send(new ProductQuantityDto(productId, 1L));
      });
    });
    orderRepository.deleteAllByUserId(userId);
  }
}
