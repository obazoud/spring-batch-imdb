package com.bazoud.springbatch.imdb.data.reader;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.file.transform.FieldSet;

import static org.junit.Assert.assertEquals;

public class ImdbMovieLineTokenizerTest {
  private final int FIELD_COUNT = 11;
  private ImdbMovieLineTokenizer lineTokenizer;
  private int index = 0;

  @Before
  public void setUp() throws Exception {
    lineTokenizer = new ImdbMovieLineTokenizer();
    lineTokenizer.afterPropertiesSet();
    index = 0;
  }

  @Test
  public void test_single_movie() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"#1 Single\" (2006)\t\t\t\t\t2006-????");
    assertFiedEquals("#1 Single", fieldSet, index++);
    assertFiedEquals("2006", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("2006", fieldSet, index++);
    assertFiedEquals("????", fieldSet, index++);
    assertEquals(FIELD_COUNT, index);
  }

  @Test
  public void test_single_movie_episode() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"#1 Single\" (2006) {Cats and Dogs (#1.4)}\t\t\t\t\t2006");
    assertFiedEquals("#1 Single", fieldSet, index++);
    assertFiedEquals("2006", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("Cats and Dogs", fieldSet, index++);
    assertFiedEquals("1", fieldSet, index++);
    assertFiedEquals("4", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("2006", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertEquals(FIELD_COUNT, index);
  }

  @Test
  public void test_single_movie_episode_date() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"+ Clair\" (2001) {(2004-09-04)}                         2004");
    assertFiedEquals("+ Clair", fieldSet, index++);
    assertFiedEquals("2001", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("2004-09-04", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("2004", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertEquals(FIELD_COUNT, index);
  }

  @Test
  public void test_single_movie_episode_name() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"(S)truth\" (2003) {Metric Time}                         2003");
    assertFiedEquals("(S)truth", fieldSet, index++);
    assertFiedEquals("2003", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("Metric Time", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("2003", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertEquals(FIELD_COUNT, index);
  }

  @Test
  public void test_tv_movie() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"A Punt, a Pass, and a Prayer\" (1968) (TV)\t\t\t\t1968");
    assertFiedEquals("A Punt, a Pass, and a Prayer", fieldSet, index++);
    assertFiedEquals("1968", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("TV", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("1968", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertEquals(FIELD_COUNT, index);
  }

  @Test
  public void test_the_terminator() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"The Terminator\" (1984)\t\t\t\t\t1984");
    assertFiedEquals("The Terminator", fieldSet, index++);
    assertFiedEquals("1984", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("1984", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertEquals(FIELD_COUNT, index);
  }

  @Test
  public void test_movie_suspended() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"'Til Death\" (2006) {No Complaints (#3.14)} {{SUSPENDED}}       ????");
    assertFiedEquals("'Til Death", fieldSet, index++);
    assertFiedEquals("2006", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("No Complaints", fieldSet, index++);
    assertFiedEquals("3", fieldSet, index++);
    assertFiedEquals("14", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("SUSPENDED", fieldSet, index++);
    assertFiedEquals("????", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertEquals(FIELD_COUNT, index);
  }

  @Test
  public void test_movie_episodes_suspended() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"Action Man\" (2000) {Various episodes} {{SUSPENDED}}	????");
    assertFiedEquals("Action Man", fieldSet, index++);
    assertFiedEquals("2000", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("Various episodes", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("SUSPENDED", fieldSet, index++);
    assertFiedEquals("????", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertEquals(FIELD_COUNT, index);
  }

  @Test
  public void test_movie_episodes_romain() {
    FieldSet fieldSet = lineTokenizer.tokenize("\"Wanted\" (2011/IV)\t\t\t\t2011");
    assertFiedEquals("Wanted", fieldSet, index++);
    assertFiedEquals("2011", fieldSet, index++);
    assertFiedEquals("IV", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertFiedEquals("2011", fieldSet, index++);
    assertFiedEquals(null, fieldSet, index++);
    assertEquals(FIELD_COUNT, index);
  }

  private void assertFiedEquals(Object expected, FieldSet fieldSet, int i) {
    assertEquals(expected, fieldSet.getValues()[i]);
  }
}
