package com.bazoud.springbatch.imdb.sql.processor;

import com.bazoud.springbatch.imdb.sql.domain.Movie;
import com.bazoud.springbatch.imdb.sql.domain.Title;
import org.springframework.batch.item.ItemProcessor;

public class MovieProcessor implements ItemProcessor<Title, Movie> {
  @Override
  public Movie process(Title item) throws Exception {
    Movie movie = new Movie();
    movie.setId(item.getId());
    movie.setName(item.getName());
    movie.setProductionYear(item.getProductionYear());
    return movie;
  }
}
