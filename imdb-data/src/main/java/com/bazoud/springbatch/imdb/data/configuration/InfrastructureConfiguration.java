package com.bazoud.springbatch.imdb.data.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

public interface InfrastructureConfiguration {

  @Bean
  DataSource dataSource();

  @Bean
  JdbcTemplate jdbcTemplate();
}