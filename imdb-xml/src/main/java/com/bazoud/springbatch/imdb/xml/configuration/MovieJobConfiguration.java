package com.bazoud.springbatch.imdb.xml.configuration;

import com.bazoud.springbatch.imdb.xml.configuration.step.MovieStepConfiguration;
import com.bazoud.springbatch.imdb.xml.configuration.step.StatsStepConfiguration;
import com.bazoud.springbatch.imdb.xml.configuration.step.UnzipStepConfiguration;
import com.bazoud.springbatch.imdb.xml.listener.TraceJobExecutionListener;
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
@Import(value = {MovieStepConfiguration.class, StatsStepConfiguration.class, UnzipStepConfiguration.class})
public class MovieJobConfiguration {
  @Autowired
  private JobBuilderFactory jobBuilders;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Autowired
  @Qualifier("unzipStep")
  private Step unzipStep;

  @Autowired
  @Qualifier("movieStep")
  private Step movieStep;

  @Autowired
  @Qualifier("statsStep")
  private Step statsStep;

  @Bean
  public Job movieJob() throws Exception {
    return jobBuilders.get("movieJob")
         .listener(traceJobExecutionListener())
         .start(unzipStep)
         .next(movieStep)
         .next(statsStep)
        .build();

    /*
    return jobBuilders.get("movieJob")
        .listener(traceJobExecutionListener())
        .start(unzipStep)
            .on("*").to(departmentStep)
            .on("FAILED").to(failedStep)
        .from(movieStep)
            .on("*").to(townStep)
            .on("FAILED").to(failedStep)
        .from(staticStep)
          .on("FAILED").to(failedStep)
        .from(failedStep)
          .on("*").fail()
        .end()
        .build();
    */
  }

  @Bean
  public TraceJobExecutionListener traceJobExecutionListener() {
    return new TraceJobExecutionListener();
  }

}
