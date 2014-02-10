package com.bazoud.springbatch.imdb.data.reader;

import org.springframework.batch.item.file.transform.RegexLineTokenizer;
import org.springframework.beans.factory.InitializingBean;

public class ImdbMovieLineTokenizer extends RegexLineTokenizer implements InitializingBean {
  private String DATE_PATTERN = "(\\d{4}||\\?{4})";

  private String MOVIE_NAME_PATTERN = "\"(.+?)\"";

  // private String RELEASE_DATE_PATTERN = "\\s\\((?:(?:(\\d{4})||\\?{4})(?:/[IXV]+)?\\)?)\\)";
  private String RELEASE_DATE_PATTERN = "\\(" + DATE_PATTERN + "\\)";

  private String TV_PATTERN = "(?:\\s\\((?:(?:V)||(?:TV)||(?:VG))\\))?";
  private String EPISODE_PATTERN = "(?:\\s+\\{(.+?)?(?:\\s+\\((?:#(?:(\\d+?)\\.(\\d+?)))?(\\d{4}-\\d{2}-\\d{2})?\\))?\\})?";

  //private String BROADCAST_DATE_PATTERN = "(?:\\s*\\t(?:(\\d{4})||\\?{4})(?:-(?:(\\d{4})||\\?{4}))?)";
  private String BROADCAST_DATE_PATTERN = "(?:\\s*(?:(\\d\\d\\d\\d)||\\?\\?\\?\\?)(?:-(?:(\\d\\d\\d\\d)||\\?\\?\\?\\?))?)";

  private String COMMENTARY = "(?:\\s+(.+))?";

  @Override
  public void afterPropertiesSet() throws Exception {
    // "#1 Single" (2006)                                      2006-????
    // "#1 Single" (2006) {Cats and Dogs (#1.4)}               2006
    // "A Punt, a Pass, and a Prayer" (1968) (TV)                1968
    // "The Terminator" (1984)                                   1984

    // "Movie name" (year) (serie TV) {episode name (#season.episode)} (year) (comment)
    // (TV) : TV movie
    // (V)  : made for video movie

    setRegex("^"
        + MOVIE_NAME_PATTERN
        + "\\s+"
        + RELEASE_DATE_PATTERN
        + "\\s+"
        + BROADCAST_DATE_PATTERN
        + "$");
  }
}
