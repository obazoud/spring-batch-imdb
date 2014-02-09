package com.bazoud.springbatch.imdb.xml.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public interface InfrastructureConfiguration {

  @Bean
  public abstract DataSource dataSource();

  @Bean
  JdbcTemplate jdbcTemplate();

}