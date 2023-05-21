package com.team.orderservice.view;

import com.team.orderservice.infrastructure.external.ProductServiceClient;
import com.team.orderservice.infrastructure.external.UserServiceClient;
import com.team.orderservice.model.Address;
import com.team.orderservice.model.Coordinate;
import com.team.orderservice.model.Order;
import com.team.orderservice.model.Status;
import com.team.orderservice.view.dto.*;
import com.team.orderservice.view.mapper.AddressMapper;
import com.team.orderservice.view.mapper.CoordinateMapper;
import com.team.orderservice.view.mapper.OrderMapper;
import com.team.orderservice.view.mapper.StatusMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperFacade {
  private final AddressMapper.Request.Common commonRequestAddressMapper;
  private final AddressMapper.Response.Common commonResponseAddressMapper;
  private final CoordinateMapper.Request.Common commonRequestCoordinateMapper;
  private final CoordinateMapper.Response.Common commonResponseCoordinateMapper;
  private final OrderMapper.Request.Common commonRequestOrderMapper;
  private final OrderMapper.Request.Create createRequestOrderMapper;
  private final OrderMapper.Response.Common commonResponseOrderMapper;
  private final OrderMapper.Response.Simple simpleResponseOrderMapper;
  private final StatusMapper.Request.Common commonRequestStatusMapper;
  private final StatusMapper.Response.Common commonResponseStatusMapper;
  private final UserServiceClient userServiceClient;
  private final ProductServiceClient productServiceClient;
  

  public MapperFacade(AddressMapper.Request.Common commonRequestAddressMapper,
                      AddressMapper.Response.Common commonResponseAddressMapper,
                      CoordinateMapper.Request.Common commonRequestCoordinateMapper,
                      CoordinateMapper.Response.Common commonResponseCoordinateMapper,
                      OrderMapper.Request.Common commonRequestOrderMapper,
                      OrderMapper.Request.Create createRequestOrderMapper,
                      OrderMapper.Response.Common commonResponseOrderMapper,
                      OrderMapper.Response.Simple simpleResponseOrderMapper,
                      StatusMapper.Request.Common commonRequestStatusMapper,
                      StatusMapper.Response.Common commonResponseStatusMapper,
                      UserServiceClient userServiceClient,
                      ProductServiceClient productServiceClient) {
    this.commonRequestAddressMapper = commonRequestAddressMapper;
    this.commonResponseAddressMapper = commonResponseAddressMapper;
    this.commonRequestCoordinateMapper = commonRequestCoordinateMapper;
    this.commonResponseCoordinateMapper = commonResponseCoordinateMapper;
    this.commonRequestOrderMapper = commonRequestOrderMapper;
    this.createRequestOrderMapper = createRequestOrderMapper;
    this.commonResponseOrderMapper = commonResponseOrderMapper;
    this.simpleResponseOrderMapper = simpleResponseOrderMapper;
    this.commonRequestStatusMapper = commonRequestStatusMapper;
    this.commonResponseStatusMapper = commonResponseStatusMapper;
    this.userServiceClient = userServiceClient;
    this.productServiceClient = productServiceClient;
  }

  public Address commonRequestAddressToDomain(AddressDto.Request.Common dto) {
    return commonRequestAddressMapper.toDomain(dto);
  }

  public AddressDto.Response.Common toCommonResponseAddressDto(Address address) {
    return commonResponseAddressMapper.toDto(address);
  }

  public Coordinate commonRequestCoordinateToDomain(CoordinateDto.Request.Common dto) {
    return commonRequestCoordinateMapper.toDomain(dto);
  }

  public CoordinateDto.Response.Common toCommonResponseCoordinateDto(Coordinate coordinate) {
    return commonResponseCoordinateMapper.toDto(coordinate);
  }

  public Order commonRequestOrderToDomain(OrderDto.Request.Common dto) {
    return commonRequestOrderMapper.toDomain(dto);
  }

  public Order createRequestOrderToDomain(OrderDto.Request.Create dto) {
    return createRequestOrderMapper.toDomain(dto);
  }

  public OrderDto.Response.Common toCommonResponseOrderDto(Order order, Long userId, String token) {
    UserDto.Response.Common userDto = userServiceClient.get(userId, token);
    List<ProductDto.Response.Common> productDtoList = productServiceClient.getAll(order.getProducts(), token);
    return commonResponseOrderMapper.toDto(order, userDto, productDtoList);
  }

  public OrderDto.Response.Simple toSimpleResponseOrderDto(Order order) {
    return simpleResponseOrderMapper.toDto(order);
  }

  public Status commonRequestStatusToDomain(StatusDto.Request.Common dto) {
    return commonRequestStatusMapper.toDomain(dto);
  }

  public StatusDto.Response.Common toCommonResponseStatusDto(Status status) {
    return commonResponseStatusMapper.toDto(status);
  }
}
