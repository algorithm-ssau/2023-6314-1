package com.team.orderservice.mapper.impl;

import com.team.orderservice.data.Address;
import com.team.orderservice.dto.AddressResponseDto;
import com.team.orderservice.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressResponseDtoMapper implements ObjectMapper<Address, AddressResponseDto> {
  private final CoordinateResponseDtoMapper coordinateResponseDtoMapper;
  @Override
  public AddressResponseDto map(Address from) {
    return new AddressResponseDto(
      from.getId(),
      from.getStreet(),
      from.getCity(),
      from.getRegion(),
      from.getCountry(),
      from.getZipcode(),
      coordinateResponseDtoMapper.map(from.getCoordinate())
    );
  }
}
