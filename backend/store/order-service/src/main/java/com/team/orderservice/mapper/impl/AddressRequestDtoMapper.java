package com.team.orderservice.mapper.impl;

import com.team.orderservice.data.Address;
import com.team.orderservice.dto.AddressRequestDto;
import com.team.orderservice.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressRequestDtoMapper implements ObjectMapper<AddressRequestDto, Address> {
  private final CoordinateRequestDtoMapper coordinateRequestDtoMapper;
  @Override
  public Address map(AddressRequestDto from) {
    return new Address(
      from.getStreet(),
      from.getCity(),
      from.getRegion(),
      from.getCountry(),
      from.getZipcode(),
      coordinateRequestDtoMapper.map(from.getCoordinate())
    );
  }
}
