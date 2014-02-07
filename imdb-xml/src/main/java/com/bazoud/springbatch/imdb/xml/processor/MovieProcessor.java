package com.bazoud.springbatch.imdb.xml.processor;

import com.bazoud.springbatch.imdb.xml.domain.Movie;
import org.springframework.batch.item.ItemProcessor;

public class MovieProcessor implements ItemProcessor<Movie, Movie> {

  @Override
  public Movie process(Movie item) throws Exception {
    return item;
  }
}
