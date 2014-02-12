package com.bazoud.springbatch.imdb.data.reader;

import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.core.io.ByteArrayResource;

import static org.junit.Assert.assertEquals;
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
}
