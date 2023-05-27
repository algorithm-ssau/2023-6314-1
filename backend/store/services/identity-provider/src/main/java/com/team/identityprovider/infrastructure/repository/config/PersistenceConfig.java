package com.team.identityprovider.infrastructure.repository.config;

import com.team.identityprovider.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfig {
  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Bean
  public DataSource dataSource() {
    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.url(datasourceUrl);
    dataSourceBuilder.driverClassName(driverClassName);
    dataSourceBuilder.username(username);
    dataSourceBuilder.password(password);
    return dataSourceBuilder.build();
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  public RowMapper<User> userAuthRowMapper() {
    return (rs, rowNum) -> new User(
      rs.getLong("id"),
      rs.getString("name"),
      rs.getString("email"),
      rs.getString("password"),
      rs.getBoolean("active"),
      rs.getString("role")
    );
  }
}
