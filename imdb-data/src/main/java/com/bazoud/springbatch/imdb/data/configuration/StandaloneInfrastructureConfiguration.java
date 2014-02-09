package com.bazoud.springbatch.imdb.data.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
@EnableBatchProcessing
@Profile("dev")
public class StandaloneInfrastructureConfiguration implements InfrastructureConfiguration {
  @Value("${dataSource.driverClass:com.mysql.jdbc.Driver}")
  private Class driverClass;
  @Value("${dataSource.url:jdbc:mysql://localhost:3307/imdb}")
  private String url;
  @Value("${dataSource.username:root}")
  private String username;
  @Value("${dataSource.password:root}")
  private String password;

  @Autowired
  private ResourceLoader resourceLoader;

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriverClass(driverClass);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }


}