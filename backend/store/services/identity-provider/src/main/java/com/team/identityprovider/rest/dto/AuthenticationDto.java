package com.team.identityprovider.rest.dto;

import lombok.Value;

@Value
public class AuthenticationDto {
  String email;
  String password;
}
