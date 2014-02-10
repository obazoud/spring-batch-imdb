package com.bazoud.springbatch.imdb.data.configuration.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.bazoud.springbatch.imdb.data.configuration.InfrastructureConfiguration;
import com.bazoud.springbatch.imdb.data.domain.Movie;
import com.bazoud.springbatch.imdb.data.reader.ImdbMovieLineTokenizer;
import com.bazoud.springbatch.imdb.data.reader.ImdbSkipHeaderPolicy;
import com.bazoud.springbatch.imdb.data.reader.MyFileFlatReader;
import com.bazoud.springbatch.imdb.data.reader.SkipHeaderPolicy;
import com.google.common.collect.ImmutableMap;

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
        .writer(writer())
        .build();
  }

  @Bean(name = "movieReader")
  public ItemReader<Movie> reader() throws Exception {
    MyFileFlatReader<Movie> reader = new MyFileFlatReader<>();
    reader.setEncoding("ISO-8959-1");
    reader.setSkipHeaderPolicy(skipHeaderPolicy());

    DefaultLineMapper<Movie> lineMapper = new DefaultLineMapper<Movie>();
    lineMapper.setLineTokenizer(new ImdbMovieLineTokenizer());

    BeanWrapperFieldSetMapper<Movie> fieldSetMapper = new BeanWrapperFieldSetMapper<Movie>();
    lineMapper.setFieldSetMapper(fieldSetMapper);

    reader.setLineMapper(lineMapper);
    reader.setResource(new FileSystemResource("/tmp/movies.xml"));
    return reader;
  }

  @Bean(name = "movieReader")
  public SkipHeaderPolicy skipHeaderPolicy() {
    return new ImdbSkipHeaderPolicy("MOVIES LIST", 2);
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
