package com.team.orderservice.mapper;

import com.team.orderservice.data.Order;
import com.team.orderservice.dto.OrderDto;
import com.team.orderservice.startup.SetupOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

public enum OrderMapper {;
  public enum Request {;
    @Component
    @RequiredArgsConstructor
    public static final class Common {
      private final AddressMapper.Request.Common commonAddressMapper;

      public Order toDomain(OrderDto.Request.Common dto) {
        return new Order(
          commonAddressMapper.toDomain(dto.getAddress()),
          dto.getProducts(),
          dto.getUserId(),
          dto.getArrivalDateTime()
        );
      }
    }
  }

  public enum Response {;
    @Component
    @RequiredArgsConstructor
    public static final class Common {
      private final AddressMapper.Response.Common commonAddressMapper;
      private final StatusMapper.Response.Common commonStatusMapper;

      public OrderDto.Response.Common toDto(Order order) {
        return new OrderDto.Response.Common(
          order.getId(),
          commonAddressMapper.toDto(order.getArrivalAddress()),
          commonStatusMapper.toDto(order.getStatus()),
          order.getProducts(),
          order.getPayloadDateTime(),
          order.getArrivalDateTime()
        );
      }
    }
  }

  public enum Startup {;
    @Component
    public static final class Common {
      public Order toDomain(SetupOrder setupOrder) {
        return new Order(
          setupOrder.getArrivalAddress(),
          setupOrder.getProducts(),
          setupOrder.getUserId(),
          setupOrder.getArrivalDateTime()
        );
      }
    }
  }
}
