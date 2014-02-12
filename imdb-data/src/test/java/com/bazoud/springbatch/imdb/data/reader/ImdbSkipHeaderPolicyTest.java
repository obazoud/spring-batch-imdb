package com.bazoud.springbatch.imdb.data.reader;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ImdbSkipHeaderPolicyTest {

  @Test
  public void test_no_skip() {
    int line = 0;
    SkipHeaderPolicy policy = new ImdbSkipHeaderPolicy("TEST", 0);
    assertTrue(policy.shouldSkip("TEST", line ++));
    assertFalse(policy.shouldSkip("xxx", line++));
  }

  @Test
  public void test_with_marker_and_no_skip() {
    int line = 0;
    SkipHeaderPolicy policy = new ImdbSkipHeaderPolicy("TEST", 0);
    assertTrue(policy.shouldSkip("xxx", line ++));
    assertTrue(policy.shouldSkip("TEST", line ++));
    assertFalse(policy.shouldSkip("xxx", line++));
  }

  @Test
  public void test_with_marker_and_skip_some_lines() {
    int line = 0;
    SkipHeaderPolicy policy = new ImdbSkipHeaderPolicy("TEST", 1);
    assertTrue(policy.shouldSkip("xxx", line ++));
    assertTrue(policy.shouldSkip("TEST", line ++));
    assertTrue(policy.shouldSkip("xxx", line++));
    assertFalse(policy.shouldSkip("xxx", line++));
  }

  @Test
  public void test_with_marker_and_read_empty_line_and_skip_some_lines() {
    int line = 0;
    SkipHeaderPolicy policy = new ImdbSkipHeaderPolicy("TEST", 2);
    assertTrue(policy.shouldSkip("xxx", line ++));
    assertTrue(policy.shouldSkip("TEST", line ++));
    assertTrue(policy.shouldSkip("xxx", line++));
    assertTrue(policy.shouldSkip("", line++));
    assertFalse(policy.shouldSkip("xxx", line++));
  }
}
