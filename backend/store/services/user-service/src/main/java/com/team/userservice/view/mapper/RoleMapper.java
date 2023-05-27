package com.team.userservice.view.mapper;

import com.team.userservice.view.dto.RoleDto;
import com.team.userservice.model.Role;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class RoleMapper {
  public Role toDomain(RoleDto roleDto) {
    return Role.forValue("ROLE_" + roleDto.toValue().toUpperCase(Locale.ROOT));
  }

  public RoleDto toDto(Role role) {
    return  RoleDto.forValue(role.getName().split("ROLE_")[1].toLowerCase());
  }
}
