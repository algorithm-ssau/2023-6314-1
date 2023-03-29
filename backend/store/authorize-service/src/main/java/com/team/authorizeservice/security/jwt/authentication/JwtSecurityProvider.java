package com.team.authorizeservice.security.jwt.authentication;

import com.team.authorizeservice.security.jwt.exception.JwtAuthenticationException;
import com.team.authorizeservice.security.jwt.exception.TokenResolvingException;
import com.team.authorizeservice.security.jwt.properties.TokenPropertiesExtractor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtSecurityProvider {
  private final UserDetailsService userDetailsService;
  private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
  private final TokenPropertiesExtractor.TokenData refreshTokenData;

  @Autowired
  public JwtSecurityProvider(
    UserDetailsService userDetailsService,
    TokenPropertiesExtractor tokenPropertiesExtractor
  ) {
    this.userDetailsService = userDetailsService;
    this.refreshTokenData = tokenPropertiesExtractor.pullRefreshTokenData();
  }

  public String resolveRefreshToken(HttpServletRequest request) {

    var cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
        return cookie.getValue();
      }
    }
    throw new TokenResolvingException("Token not been presented in cookie with name: " + REFRESH_TOKEN_COOKIE_NAME);
  }

  public boolean validateRefreshToken(String token) {
    try {
      return !extractRefreshTokenClaims(token).getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException ex) {
      throw new JwtAuthenticationException("JWT token is expired or invalid");
    }
  }

  public Authentication loadAuthenticationByRefreshToken(String token) {
    var username = extractRefreshTokenClaims(token).getSubject();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private Claims extractRefreshTokenClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(refreshTokenData.getSecretKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }
}
