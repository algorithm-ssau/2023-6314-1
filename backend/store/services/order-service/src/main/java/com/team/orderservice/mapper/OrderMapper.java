package com.team.orderservice.mapper;

import com.team.orderservice.model.Order;
import com.team.orderservice.dto.OrderDto;
import com.team.orderservice.dto.ProductDto;
import com.team.orderservice.dto.UserDto;
import com.team.orderservice.startup.SetupOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

public enum OrderMapper {;
  public enum Request {;
    @Component
    @RequiredArgsConstructor
    public static final class Common {
      private final AddressMapper.Request.Common commonAddressMapper;

      public Order toDomain(OrderDto.Request.Common dto) {
        return new Order(
          commonAddressMapper.toDomain(dto.getAddress()),
          dto.getProducts().stream().map(ProductDto.Response.Common::getId).toList(),
          dto.getUser().getId(),
          dto.getArrivalDateTime()
        );
      }
    }

    @Component
    @RequiredArgsConstructor
    public static final class Create {
      private final AddressMapper.Request.Common commonAddressMapper;

      public Order toDomain(OrderDto.Request.Create dto) {
        return new Order(
          commonAddressMapper.toDomain(dto.getAddress()),
          dto.getProductsIds(),
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

      public OrderDto.Response.Common toDto(Order order,
                                            UserDto.Response.Common user,
                                            List<ProductDto.Response.Common> products) {
        return new OrderDto.Response.Common(
          order.getId(),
          commonAddressMapper.toDto(order.getArrivalAddress()),
          commonStatusMapper.toDto(order.getStatus()),
          products,
          order.getPayloadDateTime(),
          order.getArrivalDateTime(),
          user
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
