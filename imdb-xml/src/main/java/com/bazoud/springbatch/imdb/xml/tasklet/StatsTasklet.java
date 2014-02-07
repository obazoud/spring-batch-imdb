package com.bazoud.springbatch.imdb.xml.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class StatsTasklet implements Tasklet {
  private static final Logger LOG = LoggerFactory.getLogger(StatsTasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOG.warn("StatsTaslet : ");
    return RepeatStatus.FINISHED;
  }
}
