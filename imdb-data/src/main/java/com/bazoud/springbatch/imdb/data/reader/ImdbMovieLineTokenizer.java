package com.bazoud.springbatch.imdb.data.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.RegexLineTokenizer;
import org.springframework.beans.factory.InitializingBean;

public class ImdbMovieLineTokenizer extends RegexLineTokenizer implements InitializingBean {
  private static final Logger LOG = LoggerFactory.getLogger(ImdbMovieLineTokenizer.class);
  private String DATE_PATTERN = "(\\d{4}||\\?{4})";

  private String MOVIE_NAME_PATTERN = "\"(.+?)\"";

  // private String RELEASE_DATE_PATTERN = "\\s\\((?:(?:(\\d{4})||\\?{4})(?:/[IXV]+)?\\)?)\\)";
  private String RELEASE_DATE_PATTERN = "\\((?:(\\d{4}||\\?{4}))(?:\\/([IXV]+))?\\)";

  private String TV_PATTERN = "(?:\\(((?:V)||(?:TV)||(?:VG))\\))?";
  private String EPISODE_PATTERN = "(?:\\s+\\{(.+?)?(?:\\s+\\((?:#(?:(\\d+?)\\.(\\d+?)))?(\\d{4}-\\d{2}-\\d{2})?\\))?\\})?";

  //private String BROADCAST_DATE_PATTERN = "(?:\\s*\\t(?:(\\d{4})||\\?{4})(?:-(?:(\\d{4})||\\?{4}))?)";
  private String BROADCAST_DATE_PATTERN = "(?:(\\d{4}||\\?{4})(?:\\-(?:(\\d{4}||\\?{4})))?)";

  private String COMMENTARY = "(?:\\s+(.+))?";

  @Override
  public void afterPropertiesSet() throws Exception {
    // "Title" (year) (type) {episode title (#season number.episode number)} (year) (comment)
    String pattern = "^"
        + MOVIE_NAME_PATTERN
        + "\\s+"
        + RELEASE_DATE_PATTERN
        + "\\s+"
        + TV_PATTERN
        + "\\s+"
        + EPISODE_PATTERN
        + "\\s+"
        + BROADCAST_DATE_PATTERN
        + "$";
    LOG.info(pattern);
    setRegex(pattern);
  }
}
