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
  public void test_single_movie() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"#1 Single\" (2006)\t\t\t\t\t2006-????");
    assertEquals(9, fieldSet.getFieldCount());
    assertEquals("#1 Single", fieldSet.getValues()[0]);
    assertEquals("2006", fieldSet.getValues()[1]);
    assertEquals(null, fieldSet.getValues()[2]);
    assertEquals(null, fieldSet.getValues()[3]);
    assertEquals(null, fieldSet.getValues()[4]);
    assertEquals(null, fieldSet.getValues()[5]);
    assertEquals("2006", fieldSet.getValues()[6]);
    assertEquals("????", fieldSet.getValues()[7]);
  }

  @Test
  public void test_single_movie_episode() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"#1 Single\" (2006) {Cats and Dogs (#1.4)}\t\t\t\t\t2006");
    assertEquals(9, fieldSet.getFieldCount());
    assertEquals("#1 Single", fieldSet.getValues()[0]);
    assertEquals("2006", fieldSet.getValues()[1]);
    assertEquals(null, fieldSet.getValues()[2]);
    assertEquals("Cats and Dogs", fieldSet.getValues()[3]);
    assertEquals("1", fieldSet.getValues()[4]);
    assertEquals("4", fieldSet.getValues()[5]);
    assertEquals("2006", fieldSet.getValues()[6]);
    assertEquals(null, fieldSet.getValues()[7]);
  }

  @Test
  public void test_tv_movie() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"A Punt, a Pass, and a Prayer\" (1968) (TV)\t\t\t\t1968");
    assertEquals(9, fieldSet.getFieldCount());
    assertEquals("A Punt, a Pass, and a Prayer", fieldSet.getValues()[0]);
    assertEquals("1968", fieldSet.getValues()[1]);
    assertEquals("TV", fieldSet.getValues()[2]);
    assertEquals(null, fieldSet.getValues()[3]);
    assertEquals(null, fieldSet.getValues()[4]);
    assertEquals(null, fieldSet.getValues()[5]);
    assertEquals(null, fieldSet.getValues()[6]);
    assertEquals("1968", fieldSet.getValues()[7]);
    assertEquals(null, fieldSet.getValues()[8]);
  }

  @Test
  public void test_the_terminator() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"The Terminator\" (1984)\t\t\t\t\t1984");
    assertEquals(9, fieldSet.getFieldCount());
    assertEquals("The Terminator", fieldSet.getValues()[0]);
    assertEquals("1984", fieldSet.getValues()[1]);
    assertEquals(null, fieldSet.getValues()[2]);
    assertEquals(null, fieldSet.getValues()[3]);
    assertEquals(null, fieldSet.getValues()[4]);
    assertEquals(null, fieldSet.getValues()[5]);
    assertEquals(null, fieldSet.getValues()[6]);
    assertEquals("1984", fieldSet.getValues()[7]);
    assertEquals(null, fieldSet.getValues()[8]);
  }

}
