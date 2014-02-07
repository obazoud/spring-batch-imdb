package com.bazoud.springbatch.imdb.sql.processor;

import com.bazoud.springbatch.imdb.sql.domain.Movie;
import org.springframework.batch.item.ItemProcessor;

public class SummaryProcessor implements ItemProcessor<Movie, Movie> {
  @Override
  public Movie process(Movie item) throws Exception {
    return item;
  }
}
