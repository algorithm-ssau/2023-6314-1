package com.team.orderservice.view.controller;

import com.team.jwt.authentication.JwtAuthenticationToken;
import com.team.jwt.properties.TokenMetadata;
import com.team.jwt.service.JwtSecurityProvider;
import com.team.orderservice.model.Order;
import com.team.orderservice.service.contract.OrderService;
import com.team.orderservice.view.MapperFacade;
import com.team.orderservice.view.dto.OrderDto;
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
  private final MapperFacade mapperFacade;
  private final JwtSecurityProvider jwtSecurityProvider;
  private final TokenMetadata tokenMetadata;

  @Autowired
  public OrderController(OrderService orderService,
                         MapperFacade mapperFacade,
                         JwtSecurityProvider jwtSecurityProvider,
                         TokenMetadata tokenMetadata) {
    this.orderService = orderService;
    this.mapperFacade = mapperFacade;
    this.jwtSecurityProvider = jwtSecurityProvider;
    this.tokenMetadata = tokenMetadata;
  }

  @GetMapping
  public ResponseEntity<List<OrderDto.Response.Common>> getAll(JwtAuthenticationToken authenticationToken) {
    String token = authenticationToken.getPrincipal();
    var orderResponseDtos = orderService.getAll().stream()
      .map((Order order) -> mapperFacade.toCommonResponseOrderDto(order, order.getUserId(), token))
      .toList();
    return ResponseEntity.ok().body(orderResponseDtos);
  }

  @PostMapping
  public ResponseEntity<OrderDto.Response.Common> create(@Valid @RequestBody OrderDto.Request.Create orderRequestDto) {
    Order order = mapperFacade.createRequestOrderToDomain(orderRequestDto);
    orderService.create(order);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/mine")
  public ResponseEntity<List<OrderDto.Response.Simple>> getMineOrders(JwtAuthenticationToken authenticationToken) {
    String token = authenticationToken.getPrincipal();
    Long userId = jwtSecurityProvider.parseUserId(token, tokenMetadata);
    List<Order> userOrders = orderService.getAllByUserId(userId);
    List<OrderDto.Response.Simple> dtos = userOrders.stream().map(mapperFacade::toSimpleResponseOrderDto).toList();
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDto.Response.Common> getById(@PathVariable Long id, JwtAuthenticationToken authentication) {
    String token = authentication.getPrincipal();
    Order order = orderService.getById(id);
    OrderDto.Response.Common orderResponseDto = mapperFacade.toCommonResponseOrderDto(order, order.getUserId(), token);
    return ResponseEntity.ok().body(orderResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<OrderDto.Response.Common> deleteById(@PathVariable Long id) {
    orderService.cancelById(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/mine")
  public ResponseEntity<OrderDto.Response.Common> deleteMineOrders(JwtAuthenticationToken authenticationToken) {
    ResponseEntity<List<OrderDto.Response.Common>> all = getAll(authenticationToken);
    List<OrderDto.Response.Common> body = Objects.requireNonNull(all.getBody());
    body.forEach(dto -> orderService.cancelById(dto.getBase().getId()));
    return ResponseEntity.ok().build();
  }
}
