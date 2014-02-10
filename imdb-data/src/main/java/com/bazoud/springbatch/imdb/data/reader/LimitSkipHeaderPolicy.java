package com.bazoud.springbatch.imdb.data.reader;

import org.springframework.batch.item.file.transform.SkipHeaderPolicy;

public class LimitSkipHeaderPolicy implements SkipHeaderPolicy {
  private int limit;

  public LimitSkipHeaderPolicy(int limit) {
    this.limit = limit;
  }

  @Override
  public boolean shouldSkip(String line, int linesToSkip) {
    return this.limit < linesToSkip;
  }
}
