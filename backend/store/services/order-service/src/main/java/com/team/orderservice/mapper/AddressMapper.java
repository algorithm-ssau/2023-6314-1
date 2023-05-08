package com.team.orderservice.mapper;

import com.team.orderservice.model.Address;
import com.team.orderservice.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

public enum AddressMapper {;
  public enum Request {;
    @Component
    @RequiredArgsConstructor
    public static final class Common {
      private final CoordinateMapper.Request.Common commonCoordinateMapper;

      public Address toDomain(AddressDto.Request.Common dto) {
        return new Address(
          dto.getStreet(),
          dto.getCity(),
          dto.getRegion(),
          dto.getCountry(),
          dto.getZipcode(),
          commonCoordinateMapper.toDomain(dto.getCoordinate())
        );
      }
    }
  }

  public enum Response {;
    @Component
    @RequiredArgsConstructor
    public static final class Common {
      private final CoordinateMapper.Response.Common commonCoordinateMapper;

      public AddressDto.Response.Common toDto(Address address) {
        return new AddressDto.Response.Common(
          address.getId(),
          address.getStreet(),
          address.getCity(),
          address.getRegion(),
          address.getCountry(),
          address.getZipcode(),
          commonCoordinateMapper.toDto(address.getCoordinate())
        );
      }
    }
  }
}
