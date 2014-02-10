package com.bazoud.springbatch.imdb.data.reader;

/**
 * @author Olivier Bazoud
 */
public interface SkipHeaderPolicy {
    boolean shouldSkip(String line, int linesToSkip);
}
