package com.bazoud.springbatch.imdb.data.reader;

public class ImdbSkipHeaderPolicy implements SkipHeaderPolicy {
  private String marker;
  private int skip;

  private int markerLine;
  private boolean found;

  public ImdbSkipHeaderPolicy(String marker, int skip) {
    this.marker = marker;
    this.skip = skip;
  }

  @Override
  public boolean shouldSkip(String line, int linesToSkip) {
    if (!found) {
      if (marker.equals(line)) {
        found = true;
        markerLine = linesToSkip;
      }
    }

    if (found) {
      return (markerLine + skip) < linesToSkip;
    } else {
      return true;
    }
  }
}
