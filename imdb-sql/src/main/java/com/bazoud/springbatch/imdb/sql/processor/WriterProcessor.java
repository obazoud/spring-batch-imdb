package com.bazoud.springbatch.imdb.sql.processor;

import com.bazoud.springbatch.imdb.sql.domain.Movie;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class WriterProcessor implements ItemProcessor<Movie, Movie> {
  private JdbcTemplate jdbcTemplate;

  public WriterProcessor(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private String query = "select " +
      "n.name " +
      "from cast_info as dr " +
      "join name as n on n.id = dr.person_id " +
      "  where " +
      "    movie_id = ? " +
      "    and role_id = 4 ";

  @Override
  public Movie process(Movie item) throws Exception {
    List<String> writers = jdbcTemplate.queryForList(query, String.class, item.getId());
    item.setWriters(writers);
    return item;
  }
}
