package com.team.identityprovider.service.contract;

import com.team.jwt.properties.TokenMetadata;
import io.jsonwebtoken.Claims;

public interface TokenService {
  String generateToken(Claims claims, TokenMetadata tokenMetadata);
}
