package com.bazoud.springbatch.imdb.sql.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public interface InfrastructureConfiguration {

  @Bean
  DataSource dataSource();

  @Bean
  JdbcTemplate jdbcTemplate();
}