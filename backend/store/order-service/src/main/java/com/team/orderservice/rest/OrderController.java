package com.team.orderservice.rest;

import com.team.orderservice.data.Order;
import com.team.orderservice.dto.OrderRequestDto;
import com.team.orderservice.dto.OrderResponseDto;
import com.team.orderservice.dto.SummaryDto;
import com.team.orderservice.mapper.impl.OrderRequestDtoMapper;
import com.team.orderservice.mapper.impl.OrderResponseDtoMapper;
import com.team.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final OrderRequestDtoMapper orderRequestDtoMapper;
  private final OrderResponseDtoMapper orderResponseDtoMapper;

  @GetMapping
  public ResponseEntity<List<OrderResponseDto>> getAll() {
    List<Order> orderList = orderService.getAll();
    return ResponseEntity.ok().body(
      orderList.stream()
        .map(orderResponseDtoMapper::map)
        .toList()
    );
  }

  @PostMapping
  public ResponseEntity<OrderResponseDto> create(@Valid @RequestBody OrderRequestDto orderRequestDto) {
    Order order = orderRequestDtoMapper.map(orderRequestDto);
    orderService.create(order);
    return ResponseEntity.ok().build();
  }

  //TODO
  @GetMapping("/summary")
  public ResponseEntity<List<SummaryDto>> summary() {
    return ResponseEntity.ok().body(List.of(new SummaryDto()));
  }

  //TODO
  @GetMapping("/mine")
  public ResponseEntity<List<OrderResponseDto>> getMineOrders() {
    return getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDto> getById(@PathVariable Long id) {
    Order order = orderService.getById(id);
    OrderResponseDto orderResponseDto = orderResponseDtoMapper.map(order);
    return ResponseEntity.ok().body(orderResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<OrderResponseDto> deleteById(@PathVariable Long id) {
    orderService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/mine")
  public ResponseEntity<OrderResponseDto> deleteMineOrders() {
    ResponseEntity<List<OrderResponseDto>> all = getAll();
    List<OrderResponseDto> body = Objects.requireNonNull(all.getBody());
    body.forEach(dto -> orderService.deleteById(dto.getId()));
    return ResponseEntity.ok().build();
  }
}
