package com.bazoud.springbatch.imdb.xml.configuration.step;

import com.bazoud.springbatch.imdb.xml.configuration.InfrastructureConfiguration;
import com.bazoud.springbatch.imdb.xml.tasklet.StatsTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsStepConfiguration {
  @Autowired
  private StepBuilderFactory stepBuilders;

  @Autowired
  private InfrastructureConfiguration infrastructureConfiguration;

  @Bean(name = "statsStep")
  public Step step() throws Exception {
    return stepBuilders.get("statsStep")
        .tasklet(tasklet())
        .build();
  }

  @Bean(name = "statsTasklet")
  public Tasklet tasklet() {
    StatsTasklet tasklet = new StatsTasklet();
    return tasklet;
  }


}
