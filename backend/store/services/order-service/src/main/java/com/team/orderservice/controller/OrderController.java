package com.team.orderservice.controller;

import com.team.basejwt.authentication.JwtAuthenticationToken;
import com.team.basejwt.properties.TokenMetadata;
import com.team.basejwt.service.JwtSecurityProvider;
import com.team.orderservice.dto.OrderDto;
import com.team.orderservice.dto.ProductDto;
import com.team.orderservice.dto.UserDto;
import com.team.orderservice.infrastructure.external.ProductServiceClient;
import com.team.orderservice.infrastructure.external.UserServiceClient;
import com.team.orderservice.mapper.OrderMapper;
import com.team.orderservice.model.Order;
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
  private final OrderMapper.Response.Simple simpleResponseOrderMapper;
  private final UserServiceClient userServiceClient;
  private final ProductServiceClient productServiceClient;
  private final JwtSecurityProvider jwtSecurityProvider;
  private final TokenMetadata tokenMetadata;

  @Autowired
  public OrderController(OrderService orderService,
                         OrderMapper.Request.Create createRequestOrderMapper,
                         OrderMapper.Response.Common commonResponseOrderMapper,
                         OrderMapper.Response.Simple simpleResponseOrderMapper,
                         UserServiceClient userServiceClient,
                         ProductServiceClient productServiceClient,
                         JwtSecurityProvider jwtSecurityProvider,
                         TokenMetadata tokenMetadata) {
    this.orderService = orderService;
    this.createRequestOrderMapper = createRequestOrderMapper;
    this.commonResponseOrderMapper = commonResponseOrderMapper;
    this.simpleResponseOrderMapper = simpleResponseOrderMapper;
    this.userServiceClient = userServiceClient;
    this.productServiceClient = productServiceClient;
    this.jwtSecurityProvider = jwtSecurityProvider;
    this.tokenMetadata = tokenMetadata;
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

  @GetMapping("/mine")
  public ResponseEntity<List<OrderDto.Response.Simple>> getMineOrders(JwtAuthenticationToken authenticationToken) {
    String token = authenticationToken.getPrincipal();
    Long userId = jwtSecurityProvider.parseUserId(token, tokenMetadata);
    List<Order> userOrders = orderService.getAllByUserId(userId);
    List<OrderDto.Response.Simple> dtos = userOrders.stream().map(simpleResponseOrderMapper::toDto).toList();
    return ResponseEntity.ok(dtos);
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
    body.forEach(dto -> orderService.deleteById(dto.getBase().getId()));
    return ResponseEntity.ok().build();
  }
}
