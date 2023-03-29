package com.team.authorizeservice.rest.dto;

import lombok.Value;

@Value
public class AuthenticationDto {
  String email;
  String password;
}
