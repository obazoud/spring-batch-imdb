package com.bazoud.springbatch.imdb.xml.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class StatsTasklet implements Tasklet {
  private static final Logger LOG = LoggerFactory.getLogger(StatsTasklet.class);
  private JdbcTemplate jdbcTemplate;

    public StatsTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOG.warn("StatsTaslet : " + jdbcTemplate.queryForInt("select count(*) from MOVIE"));
    return RepeatStatus.FINISHED;
  }
}
