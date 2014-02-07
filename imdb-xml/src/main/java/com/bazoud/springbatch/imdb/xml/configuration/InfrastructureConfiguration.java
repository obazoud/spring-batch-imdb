package com.bazoud.springbatch.imdb.xml.configuration;

import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public interface InfrastructureConfiguration {

  @Bean
  public abstract DataSource dataSource();

}