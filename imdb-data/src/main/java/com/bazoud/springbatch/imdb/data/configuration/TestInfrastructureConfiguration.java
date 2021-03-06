package com.bazoud.springbatch.imdb.data.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@EnableBatchProcessing
@Profile("test")
public class TestInfrastructureConfiguration implements InfrastructureConfiguration {

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
    return embeddedDatabaseBuilder
        .addScript("classpath:schema-imdb.sql")
        .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
        .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
        .setType(EmbeddedDatabaseType.H2)
        .build();
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }
}