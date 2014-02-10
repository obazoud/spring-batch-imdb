package com.bazoud.springbatch.imdb.data.reader;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.file.transform.FieldSet;

import static org.junit.Assert.assertEquals;

public class ImdbMovieLineTokenizerTest {
  private ImdbMovieLineTokenizer lineTokenizer;

  @Before
  public void setUp() throws Exception {
    lineTokenizer = new ImdbMovieLineTokenizer();
    lineTokenizer.afterPropertiesSet();
  }

  @Test
  public void test_movies() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"#1 Single\" (2006)\t\t\t\t\t2006-????");
      assertEquals(4, fieldSet.getFieldCount());
    assertEquals("#1 Single", fieldSet.getValues()[0]);
    assertEquals("2006", fieldSet.getValues()[1]);
    assertEquals("2006", fieldSet.getValues()[2]);
    assertEquals("????", fieldSet.getValues()[3]);
  }

}
