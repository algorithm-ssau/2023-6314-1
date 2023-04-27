package com.team.basejwt.properties;

import lombok.Value;

import java.security.Key;

@Value
public class TokenMetadata {
  String header;
  Key secretKey;
  long validityDateInMilliseconds;
}
