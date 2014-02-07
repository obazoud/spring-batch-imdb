package com.bazoud.springbatch.imdb.sql.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;

public class TraceJobExecutionListener implements JobExecutionListener {
  private static final Logger LOG = LoggerFactory.getLogger(TraceJobExecutionListener.class);

  @Override
  public void beforeJob(JobExecution jobExecution) {
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    LOG.info("Job '" + jobExecution.getJobInstance().getJobName() + "'");
    LOG.info("  Started     : " + jobExecution.getStartTime());
    LOG.info("  Finished    : " + jobExecution.getEndTime());
    LOG.info("  Exit-Code   : " + jobExecution.getExitStatus().getExitCode());
    LOG.info("  Exit-Descr  : " + jobExecution.getExitStatus().getExitDescription());
    LOG.info("  Status      : " + jobExecution.getStatus());

    for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
      LOG.info("Step " + stepExecution.getStepName());
      LOG.info("  Status: " + stepExecution.getStatus());
      LOG.info("  Exit-Code: " + stepExecution.getExitStatus().getExitCode());
      LOG.info("  Exit-Descr: " + stepExecution.getExitStatus().getExitDescription());
      LOG.info("  WriteCount: " + stepExecution.getWriteCount());
      LOG.info("  Commits: " + stepExecution.getCommitCount());
      LOG.info("  SkipCount: " + stepExecution.getSkipCount());
      LOG.info("  Rollbacks: " + stepExecution.getRollbackCount());
      LOG.info("  Filter: " + stepExecution.getFilterCount());
      LOG.info("");
    }
  }
}
