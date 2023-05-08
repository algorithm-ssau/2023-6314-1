package com.team.orderservice.controller;

import com.team.basejwt.authentication.JwtAuthenticationToken;
import com.team.orderservice.model.Order;
import com.team.orderservice.dto.OrderDto;
import com.team.orderservice.dto.ProductDto;
import com.team.orderservice.dto.SummaryDto;
import com.team.orderservice.dto.UserDto;
import com.team.orderservice.mapper.OrderMapper;
import com.team.orderservice.infrastructure.external.ProductServiceClient;
import com.team.orderservice.infrastructure.external.UserServiceClient;
import com.team.orderservice.service.contract.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;
  private final OrderMapper.Request.Create createRequestOrderMapper;
  private final OrderMapper.Response.Common commonResponseOrderMapper;
  private final UserServiceClient userServiceClient;
  private final ProductServiceClient productServiceClient;

  @Autowired
  public OrderController(OrderService orderService,
                         OrderMapper.Request.Create createRequestOrderMapper,
                         OrderMapper.Response.Common commonResponseOrderMapper,
                         UserServiceClient userServiceClient,
                         ProductServiceClient productServiceClient) {
    this.orderService = orderService;
    this.createRequestOrderMapper = createRequestOrderMapper;
    this.commonResponseOrderMapper = commonResponseOrderMapper;
    this.userServiceClient = userServiceClient;
    this.productServiceClient = productServiceClient;
  }

  @GetMapping
  public ResponseEntity<List<OrderDto.Response.Common>> getAll(JwtAuthenticationToken authenticationToken) {
    String token = authenticationToken.getPrincipal();
    var orderResponseDtos = orderService.getAll().stream()
      .map((Order order) -> {
        UserDto.Response.Common user = userServiceClient.get(order.getUserId(), token);
        List<ProductDto.Response.Common> products = productServiceClient.getAll(order.getProducts(), token);
        return commonResponseOrderMapper.toDto(order, user, products);
      })
      .toList();
    return ResponseEntity.ok().body(orderResponseDtos);
  }

  @PostMapping
  public ResponseEntity<OrderDto.Response.Common> create(
    @Valid @RequestBody OrderDto.Request.Create orderRequestDto
  ) {
    Order order = createRequestOrderMapper.toDomain(orderRequestDto);
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
  public ResponseEntity<List<OrderDto.Response.Common>> getMineOrders(JwtAuthenticationToken authenticationToken) {
    return getAll(authenticationToken);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDto.Response.Common> getById(@PathVariable Long id, JwtAuthenticationToken authentication) {
    String token = authentication.getPrincipal();
    Order order = orderService.getById(id);
    UserDto.Response.Common user = userServiceClient.get(order.getUserId(), token);
    List<ProductDto.Response.Common> products = productServiceClient.getAll(order.getProducts(), token);
    OrderDto.Response.Common orderResponseDto = commonResponseOrderMapper.toDto(order, user, products);
    return ResponseEntity.ok().body(orderResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<OrderDto.Response.Common> deleteById(@PathVariable Long id) {
    orderService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/mine")
  public ResponseEntity<OrderDto.Response.Common> deleteMineOrders(JwtAuthenticationToken authenticationToken) {
    ResponseEntity<List<OrderDto.Response.Common>> all = getAll(authenticationToken);
    List<OrderDto.Response.Common> body = Objects.requireNonNull(all.getBody());
    body.forEach(dto -> orderService.deleteById(dto.getId()));
    return ResponseEntity.ok().build();
  }
}
