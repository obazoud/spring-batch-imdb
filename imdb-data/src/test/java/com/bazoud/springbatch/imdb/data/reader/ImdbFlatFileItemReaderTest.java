package com.bazoud.springbatch.imdb.data.reader;

import com.bazoud.springbatch.imdb.data.domain.Movie;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ByteArrayResource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ImdbFlatFileItemReaderTest {

  @Test
  public void read_without_skip_header_policy() throws Exception {
    String line = "AAAA\n";

    ImdbFlatFileItemReader<String> reader = new ImdbFlatFileItemReader<>();
    reader.setResource(new ByteArrayResource(line.getBytes()));
    reader.setLineMapper(new PassThroughLineMapper());
    reader.open(new ExecutionContext());

    assertEquals("AAAA", reader.read());
    assertNull(reader.read());
  }

  @Test
  public void test_with_marker_and_no_skip() throws Exception {
    String line = "TEST\nBBBB\n";

    ImdbFlatFileItemReader<String> reader = new ImdbFlatFileItemReader<>();
    reader.setSkipHeaderPolicy(new ImdbSkipHeaderPolicy("TEST", 0));
    reader.setResource(new ByteArrayResource(line.getBytes()));
    reader.setLineMapper(new PassThroughLineMapper());
    reader.open(new ExecutionContext());

    assertEquals("BBBB", reader.read());
    assertNull(reader.read());
  }

  @Test
  public void test_with_marker_and_skip_some_lines() throws Exception {
    String line = "AAA\nTEST\nBBBB\nCCCC\n";

    ImdbFlatFileItemReader<String> reader = new ImdbFlatFileItemReader<>();
    reader.setSkipHeaderPolicy(new ImdbSkipHeaderPolicy("TEST", 1));
    reader.setResource(new ByteArrayResource(line.getBytes()));
    reader.setLineMapper(new PassThroughLineMapper());
    reader.open(new ExecutionContext());

    assertEquals("CCCC", reader.read());
    assertNull(reader.read());
  }

  @Test
  public void test_with_marker_and_read_empty_line_and_skip_some_lines() throws Exception {
    String line = "AAA\nTEST\nBBBB\nCCCC\nDDDD\n";

    ImdbFlatFileItemReader<String> reader = new ImdbFlatFileItemReader<>();
    reader.setSkipHeaderPolicy(new ImdbSkipHeaderPolicy("TEST", 2));
    reader.setResource(new ByteArrayResource(line.getBytes()));
    reader.setLineMapper(new PassThroughLineMapper());
    reader.open(new ExecutionContext());

    assertEquals("DDDD", reader.read());
    assertNull(reader.read());
  }

  @Test
  public void test_single_movie() throws Exception {
    String lines = "\"#1 Single\" (2006)\t\t\t\t\t2006-????\n";

    ItemReader<Movie> reader = imdbFlatFileItemReader(lines);
    Movie movie = reader.read();
    assertNotNull(movie);
    assertEquals("#1 Single", movie.getTitle());
    assertEquals("2006", movie.getReleaseDate());
    assertEquals(null, movie.getStuff());
    assertEquals(null, movie.getType());
    assertEquals(null, movie.getEpisodeTitle());
    assertEquals(null, movie.getEpisodeSeason());
    assertEquals(null, movie.getEpisodeNumber());
    assertEquals(null, movie.getEpisodeDate());
    assertEquals(null, movie.getState());
    assertEquals("2006", movie.getBroadcastDateBegin());
    assertEquals("????", movie.getBroadcastDateEnd());
    assertNull(reader.read());
  }

  @Test
  public void test_single_movie_episode() throws Exception {
    String lines = "\"#1 Single\" (2006) {Cats and Dogs (#1.4)}\t\t\t\t\t2006\n";

    ItemReader<Movie> reader = imdbFlatFileItemReader(lines);
    Movie movie = reader.read();
    assertNotNull(movie);
    assertEquals("#1 Single", movie.getTitle());
    assertEquals("2006", movie.getReleaseDate());
    assertEquals(null, movie.getStuff());
    assertEquals(null, movie.getType());
    assertEquals("Cats and Dogs", movie.getEpisodeTitle());
    assertEquals("1", movie.getEpisodeSeason());
    assertEquals("4", movie.getEpisodeNumber());
    assertEquals(null, movie.getEpisodeDate());
    assertEquals(null, movie.getState());
    assertEquals("2006", movie.getBroadcastDateBegin());
    assertEquals(null, movie.getBroadcastDateEnd());
    assertNull(reader.read());
  }

  @Test
  public void test_single_movie_episode_date() throws Exception {
    String lines = "\"+ Clair\" (2001) {(2004-09-04)}                         2004\n";

    ItemReader<Movie> reader = imdbFlatFileItemReader(lines);
    Movie movie = reader.read();
    assertNotNull(movie);
    assertEquals("+ Clair", movie.getTitle());
    assertEquals("2001", movie.getReleaseDate());
    assertEquals(null, movie.getStuff());
    assertEquals(null, movie.getType());
    assertEquals(null, movie.getEpisodeTitle());
    assertEquals(null, movie.getEpisodeSeason());
    assertEquals(null, movie.getEpisodeNumber());
    assertEquals("2004-09-04", movie.getEpisodeDate());
    assertEquals(null, movie.getState());
    assertEquals("2004", movie.getBroadcastDateBegin());
    assertEquals(null, movie.getBroadcastDateEnd());
    assertNull(reader.read());

  }

  @Test
  public void test_single_movie_episode_name() throws Exception {
    String lines = "\"(S)truth\" (2003) {Metric Time}                         2003\n";

    ItemReader<Movie> reader = imdbFlatFileItemReader(lines);
    Movie movie = reader.read();
    assertNotNull(movie);
    assertEquals("(S)truth", movie.getTitle());
    assertEquals("2003", movie.getReleaseDate());
    assertEquals(null, movie.getStuff());
    assertEquals(null, movie.getType());
    assertEquals("Metric Time", movie.getEpisodeTitle());
    assertEquals(null, movie.getEpisodeSeason());
    assertEquals(null, movie.getEpisodeNumber());
    assertEquals(null, movie.getEpisodeDate());
    assertEquals(null, movie.getState());
    assertEquals("2003", movie.getBroadcastDateBegin());
    assertEquals(null, movie.getBroadcastDateEnd());
    assertNull(reader.read());
  }

  @Test
  public void test_tv_movie() throws Exception {
    String lines = "\"A Punt, a Pass, and a Prayer\" (1968) (TV)\t\t\t\t1968\n";

    ItemReader<Movie> reader = imdbFlatFileItemReader(lines);
    Movie movie = reader.read();
    assertNotNull(movie);
    assertEquals("A Punt, a Pass, and a Prayer", movie.getTitle());
    assertEquals("1968", movie.getReleaseDate());
    assertEquals(null, movie.getStuff());
    assertEquals("TV", movie.getType());
    assertEquals(null, movie.getEpisodeTitle());
    assertEquals(null, movie.getEpisodeSeason());
    assertEquals(null, movie.getEpisodeNumber());
    assertEquals(null, movie.getEpisodeDate());
    assertEquals(null, movie.getState());
    assertEquals("1968", movie.getBroadcastDateBegin());
    assertEquals(null, movie.getBroadcastDateEnd());
    assertNull(reader.read());
  }

  @Test
  public void test_the_terminator() throws Exception {
    String lines = "\"The Terminator\" (1984)\t\t\t\t\t1984\n";

    ItemReader<Movie> reader = imdbFlatFileItemReader(lines);
    Movie movie = reader.read();
    assertNotNull(movie);
    assertEquals("The Terminator", movie.getTitle());
    assertEquals("1984", movie.getReleaseDate());
    assertEquals(null, movie.getStuff());
    assertEquals(null, movie.getType());
    assertEquals(null, movie.getEpisodeTitle());
    assertEquals(null, movie.getEpisodeSeason());
    assertEquals(null, movie.getEpisodeNumber());
    assertEquals(null, movie.getEpisodeDate());
    assertEquals(null, movie.getState());
    assertEquals("1984", movie.getBroadcastDateBegin());
    assertEquals(null, movie.getBroadcastDateEnd());
    assertNull(reader.read());
  }

  @Test
  public void test_movie_suspended() throws Exception {
    String lines ="\"'Til Death\" (2006) {No Complaints (#3.14)} {{SUSPENDED}}       ????\n";

    ItemReader<Movie> reader = imdbFlatFileItemReader(lines);
    Movie movie = reader.read();
    assertNotNull(movie);
    assertEquals("'Til Death", movie.getTitle());
    assertEquals("2006", movie.getReleaseDate());
    assertEquals(null, movie.getStuff());
    assertEquals(null, movie.getType());
    assertEquals("No Complaints", movie.getEpisodeTitle());
    assertEquals("3", movie.getEpisodeSeason());
    assertEquals("14", movie.getEpisodeNumber());
    assertEquals(null, movie.getEpisodeDate());
    assertEquals("SUSPENDED", movie.getState());
    assertEquals("????", movie.getBroadcastDateBegin());
    assertEquals(null, movie.getBroadcastDateEnd());
    assertNull(reader.read());
  }

  @Test
  public void test_movie_episodes_suspended() throws Exception {
    String lines ="\"Action Man\" (2000) {Various episodes} {{SUSPENDED}}\t????\n";

    ItemReader<Movie> reader = imdbFlatFileItemReader(lines);
    Movie movie = reader.read();
    assertNotNull(movie);
    assertEquals("Action Man", movie.getTitle());
    assertEquals("2000", movie.getReleaseDate());
    assertEquals(null, movie.getStuff());
    assertEquals(null, movie.getType());
    assertEquals("Various episodes", movie.getEpisodeTitle());
    assertEquals(null, movie.getEpisodeSeason());
    assertEquals(null, movie.getEpisodeNumber());
    assertEquals(null, movie.getEpisodeDate());
    assertEquals("SUSPENDED", movie.getState());
    assertEquals("????", movie.getBroadcastDateBegin());
    assertEquals(null, movie.getBroadcastDateEnd());
    assertNull(reader.read());
  }

  @Test
  public void test_movie_episodes_romain() throws Exception {
    String lines ="\"Wanted\" (2011/IV)\t\t\t\t2011";

    ItemReader<Movie> reader = imdbFlatFileItemReader(lines);
    Movie movie = reader.read();
    assertNotNull(movie);
    assertEquals("Wanted", movie.getTitle());
    assertEquals("2011", movie.getReleaseDate());
    assertEquals("IV", movie.getStuff());
    assertEquals(null, movie.getType());
    assertEquals(null, movie.getEpisodeTitle());
    assertEquals(null, movie.getEpisodeSeason());
    assertEquals(null, movie.getEpisodeNumber());
    assertEquals(null, movie.getEpisodeDate());
    assertEquals(null, movie.getState());
    assertEquals("2011", movie.getBroadcastDateBegin());
    assertEquals(null, movie.getBroadcastDateEnd());
    assertNull(reader.read());
  }

  private ItemReader<Movie> imdbFlatFileItemReader(String lines) {
    String header = "CRC: 0xFBD4F99B\n\nCopyright\n---------\n\nMOVIES LIST\n===========\n\n";
    ImdbFlatFileItemReader<Movie> reader = new ImdbFlatFileItemReader<>();
    reader.setSkipHeaderPolicy(new ImdbSkipHeaderPolicy("MOVIES LIST", 2));
    reader.setResource(new ByteArrayResource((header + lines).getBytes()));
    DefaultLineMapper lineMapper = new DefaultLineMapper<Movie>();
    lineMapper.setLineTokenizer(new ImdbMovieLineTokenizer());
    BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
    fieldSetMapper.setTargetType(Movie.class);
    lineMapper.setFieldSetMapper(fieldSetMapper);
    reader.setLineMapper(lineMapper);
    reader.open(new ExecutionContext());
    return reader;
  }
}
