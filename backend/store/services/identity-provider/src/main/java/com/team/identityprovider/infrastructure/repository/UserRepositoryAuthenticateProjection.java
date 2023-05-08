package com.team.identityprovider.infrastructure.repository;

import com.team.identityprovider.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
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
    return jdbcTemplate.query(
      "select id, name, email, password, active, role from users where email = ? limit 1",
      new String[]{email},
      new int[]{Types.VARCHAR},
      userAuthRowMapper
    ).get(0);
  }
}

