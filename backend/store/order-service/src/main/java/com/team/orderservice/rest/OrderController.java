package com.team.orderservice.rest;

import com.team.orderservice.data.Order;
import com.team.orderservice.dto.OrderDto;
import com.team.orderservice.dto.SummaryDto;
import com.team.orderservice.mapper.OrderMapper;
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
  private final OrderMapper.Request.Common commonRequestOrderMapper;
  private final OrderMapper.Response.Common commonResponseOrderMapper;

  @GetMapping
  public ResponseEntity<List<OrderDto.Response.Common>> getAll() {
    var orderResponseDtos = orderService.getAll().stream()
      .map(commonResponseOrderMapper::toDto)
      .toList();
    return ResponseEntity.ok().body(orderResponseDtos);
  }

  @PostMapping
  public ResponseEntity<OrderDto.Response.Common> create(
    @Valid @RequestBody OrderDto.Request.Common orderRequestDto
  ) {
    Order order = commonRequestOrderMapper.toDomain(orderRequestDto);
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
  public ResponseEntity<List<OrderDto.Response.Common>> getMineOrders() {
    return getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDto.Response.Common> getById(@PathVariable Long id) {
    Order order = orderService.getById(id);
    OrderDto.Response.Common orderResponseDto = commonResponseOrderMapper.toDto(order);
    return ResponseEntity.ok().body(orderResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<OrderDto.Response.Common> deleteById(@PathVariable Long id) {
    orderService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/mine")
  public ResponseEntity<OrderDto.Response.Common> deleteMineOrders() {
    ResponseEntity<List<OrderDto.Response.Common>> all = getAll();
    List<OrderDto.Response.Common> body = Objects.requireNonNull(all.getBody());
    body.forEach(dto -> orderService.deleteById(dto.getId()));
    return ResponseEntity.ok().build();
  }
}
