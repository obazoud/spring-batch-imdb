package com.bazoud.springbatch.imdb.sql.configuration.step;

import com.bazoud.springbatch.imdb.sql.configuration.InfrastructureConfiguration;
import com.bazoud.springbatch.imdb.sql.domain.Movie;
import com.bazoud.springbatch.imdb.sql.domain.Title;
import com.bazoud.springbatch.imdb.sql.processor.CastingProcessor;
import com.bazoud.springbatch.imdb.sql.processor.CountryProcessor;
import com.bazoud.springbatch.imdb.sql.processor.DirectorProcessor;
import com.bazoud.springbatch.imdb.sql.processor.GenresProcessor;
import com.bazoud.springbatch.imdb.sql.processor.LanguageProcessor;
import com.bazoud.springbatch.imdb.sql.processor.MovieProcessor;
import com.bazoud.springbatch.imdb.sql.processor.SummaryProcessor;
import com.bazoud.springbatch.imdb.sql.processor.WriterProcessor;
import com.google.common.collect.ImmutableMap;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MovieStepConfiguration {
  @Autowired
  private StepBuilderFactory stepBuilders;

  @Autowired
  private InfrastructureConfiguration infrastructureConfiguration;

  @Bean(name = "movieStep")
  public Step step() throws Exception {
    return stepBuilders.get("movieStep")
        .<Title, Movie>chunk(50)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean(name = "movieReader")
  public ItemReader<Title> reader() throws Exception {
    JdbcCursorItemReader<Title> reader = new JdbcCursorItemReader<Title>();
    reader.setSql(
        "select " +
        "  t.id, " +
        "  t.title, " +
        "  t.production_year " +
        "from title as t " +
        "where " +
        "  t.kind_id = 1 " +
        "limit 500 "
     );
    reader.setRowMapper(new RowMapper<Title>() {
      @Override
      public Title mapRow(ResultSet rs, int rowNum) throws SQLException {
        Title title = new Title();
        title.setId(rs.getInt("id"));
        title.setName(rs.getString("title"));
        title.setProductionYear(rs.getInt("production_year"));
        return title;
      }
    });
    reader.setDataSource(infrastructureConfiguration.dataSource());
    return reader;
  }

  @Bean(name = "movieProcessor")
  public ItemProcessor<Title, Movie> processor() {
    List<ItemProcessor<?,?>> delegates = new ArrayList<ItemProcessor<?,?>>();
    delegates.add(new MovieProcessor());
    delegates.add(new DirectorProcessor(infrastructureConfiguration.jdbcTemplate()));
//    delegates.add(new WriterProcessor(infrastructureConfiguration.jdbcTemplate()));
//    delegates.add(new CountryProcessor());
//    delegates.add(new LanguageProcessor());
//    delegates.add(new CastingProcessor());
//    delegates.add(new SummaryProcessor());
//    delegates.add(new GenresProcessor());
    CompositeItemProcessor<Title, Movie> processors = new CompositeItemProcessor<Title, Movie>();
    processors.setDelegates(delegates);
    return processors;
  }

  @Bean(name = "movieWriter")
  public ItemWriter<Movie> writer() throws Exception {
    StaxEventItemWriter<Movie> writer = new StaxEventItemWriter<Movie>();
    writer.setResource(new FileSystemResource("/tmp/movies.xml"));
    writer.setMarshaller(marshaller());
    writer.setRootTagName("movies");
    writer.setOverwriteOutput(true);
    return writer;
  }

  @Bean(name = "movieMarshaller")
  public XStreamMarshaller marshaller() throws Exception {
    XStreamMarshaller marshaller = new XStreamMarshaller();
    marshaller.setAliases(ImmutableMap.of(
        "movie", Movie.class,
        "director", String.class
    ));
    marshaller.setAnnotatedClasses(Movie.class);
    return marshaller;
  }
}
