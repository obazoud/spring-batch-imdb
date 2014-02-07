package com.bazoud.springbatch.imdb.xml.configuration.step;

import com.bazoud.springbatch.imdb.xml.configuration.InfrastructureConfiguration;
import com.bazoud.springbatch.imdb.xml.domain.Movie;
import com.bazoud.springbatch.imdb.xml.processor.MovieProcessor;
import com.google.common.collect.ImmutableMap;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

@Configuration
public class MovieStepConfiguration {
  @Autowired
  private StepBuilderFactory stepBuilders;

  @Autowired
  private InfrastructureConfiguration infrastructureConfiguration;

  @Bean(name = "movieStep")
  public Step step() throws Exception {
    return stepBuilders.get("movieStep")
        .<Movie, Movie>chunk(50)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean(name = "movieReader")
  public ItemReader<Movie> reader() throws Exception {
    StaxEventItemReader<Movie> reader = new StaxEventItemReader<Movie>();
    reader.setResource(new FileSystemResource("/tmp/movies.xml"));
    reader.setFragmentRootElementName("movie");
    reader.setUnmarshaller(marshaller());
    return reader;
  }

  @Bean(name = "movieMarshaller")
  public XStreamMarshaller marshaller() throws Exception {
    XStreamMarshaller marshaller = new XStreamMarshaller();
    marshaller.setAnnotatedClasses(Movie.class);
    marshaller.setAliases(ImmutableMap.of(
        "movie", Movie.class,
        "director", String.class
    ));
    return marshaller;
  }

  @Bean(name = "movieProcessor")
  public ItemProcessor<Movie, Movie> processor() {
    return new MovieProcessor();
  }

  @Bean(name = "movieWriter")
  public ItemWriter<Movie> writer() {
    JdbcBatchItemWriter<Movie> itemWriter = new JdbcBatchItemWriter<Movie>();
    itemWriter.setSql("INSERT INTO MOVIE (ID, NAME, PRODUCTION_YEAR, DIRECTORS)" +
        " VALUES (:id, :name, :productionYear, :rawDirectors)");
    itemWriter.setDataSource(infrastructureConfiguration.dataSource());
    itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Movie>());
    return itemWriter;
  }

}
