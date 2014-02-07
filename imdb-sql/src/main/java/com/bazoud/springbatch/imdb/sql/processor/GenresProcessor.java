package com.bazoud.springbatch.imdb.sql.processor;

import com.bazoud.springbatch.imdb.sql.domain.Movie;
import org.springframework.batch.item.ItemProcessor;

public class GenresProcessor implements ItemProcessor<Movie, Movie> {
  @Override
  public Movie process(Movie item) throws Exception {
    return item;
  }
}
