package com.team.identityprovider.service.api;

import com.team.basejwt.properties.TokenMetadata;
import io.jsonwebtoken.Claims;

import java.util.Date;

public interface TokenService {
  String generateToken(Claims claims, TokenMetadata tokenMetadata);

  interface Interval {
    Date getCreated();
    Date getExpired();
  }
}
