package com.bazoud.springbatch.imdb.sql.configuration;

import com.bazoud.springbatch.imdb.sql.configuration.step.MovieStepConfiguration;
import com.bazoud.springbatch.imdb.sql.listener.TraceJobExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import(value = {MovieStepConfiguration.class })
public class MovieJobConfiguration {
  @Autowired
  private JobBuilderFactory jobBuilders;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Autowired
  @Qualifier("movieStep")
  private Step movieStep;

  @Bean
  public Job movieJob() throws Exception {
    return jobBuilders.get("movieJob")
         .listener(traceJobExecutionListener())
         .start(movieStep)
        .build();
  }

  @Bean
  public TraceJobExecutionListener traceJobExecutionListener() {
    return new TraceJobExecutionListener();
  }

}
