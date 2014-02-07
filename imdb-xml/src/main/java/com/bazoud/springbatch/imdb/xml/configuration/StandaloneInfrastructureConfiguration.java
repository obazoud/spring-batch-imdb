package com.bazoud.springbatch.imdb.xml.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
@Profile("dev")
public class StandaloneInfrastructureConfiguration implements InfrastructureConfiguration {
  @Value("${dataSource.driverClass:com.mysql.jdbc.Driver}")
  private Class driverClass;
  @Value("${dataSource.url:jdbc:mysql://localhost:3306/towns}")
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

    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.addScript(this.resourceLoader.getResource("classpath:schema-movie.sql"));
    databasePopulator.addScript(this.resourceLoader.getResource("classpath:org/springframework/batch/core/schema-drop-mysql.sql"));
    databasePopulator.addScript(this.resourceLoader.getResource("classpath:org/springframework/batch/core/schema-mysql.sql"));
    Connection connection = null;
    try {
      connection = dataSource.getConnection();
      databasePopulator.populate(connection);
    } catch (SQLException e) {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e1) {
        }
      }
    }
    return dataSource;
  }
}