package com.team.authorizeservice.persistence.repository;

import com.team.authorizeservice.persistence.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Types;

@Component
@Slf4j
public class UserRepositoryAuthenticateProjection {
  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<User> userAuthRowMapper;

  @Autowired
  public UserRepositoryAuthenticateProjection(JdbcTemplate jdbcTemplate, 
                                              RowMapper<User> userAuthRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.userAuthRowMapper = userAuthRowMapper;
  }

  public User findByEmail(String email) {
    var user = jdbcTemplate.query(
      "select id, name, email, password, active, role from users where email = ? limit 1",
      new String[]{email},
      new int[]{Types.VARCHAR},
      userAuthRowMapper
    ).get(0);
    log.debug("Selected user: {} from users database", user);

    return user;
  }
}

