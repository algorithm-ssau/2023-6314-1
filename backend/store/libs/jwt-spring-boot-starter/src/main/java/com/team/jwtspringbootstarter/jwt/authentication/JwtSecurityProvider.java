package com.team.jwtspringbootstarter.jwt.authentication;

import com.team.jwtspringbootstarter.jwt.exception.JwtAuthenticationException;
import com.team.jwtspringbootstarter.jwt.exception.TokenResolvingException;
import com.team.jwtspringbootstarter.jwt.properties.TokenPropertiesExtractor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Slf4j
public class JwtSecurityProvider {
  private static final String BEARER_PREFIX = "Bearer_";
  private final TokenPropertiesExtractor.TokenData accessTokenData;

  @Autowired
  public JwtSecurityProvider(TokenPropertiesExtractor tokenPropertiesExtractor) {
    this.accessTokenData = tokenPropertiesExtractor.pullAccessTokenData();
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(accessTokenData.getHeader());
    if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
      log.debug("Resolved token: {}", bearerToken);
      return bearerToken.substring(BEARER_PREFIX.length());
    }
    log.warn("Token: {} not have prefix: {}", bearerToken, BEARER_PREFIX);
    throw new TokenResolvingException("Token not have access token prefix: " + BEARER_PREFIX);
  }

  public boolean validateToken(String token) {
    try {
      return !extractTokenClaims(token).getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException ex) {
      throw new JwtAuthenticationException("JWT token is expired or invalid");
    }
  }

  public Authentication loadAuthenticationByToken(String token) {
    var claims = extractTokenClaims(token);
    var username = claims.getSubject();
    var authorities = extractAuthorities(claims);
    return new UsernamePasswordAuthenticationToken(username, "", authorities);
  }

  private Collection<? extends GrantedAuthority> extractAuthorities(Claims claims) {
    var collection = claims.get("authorities", Collection.class);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    for (Object o : collection) {
      authorities.add((GrantedAuthority) o);
    }
    return authorities;
  }

  private Claims extractTokenClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(accessTokenData.getSecretKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

}