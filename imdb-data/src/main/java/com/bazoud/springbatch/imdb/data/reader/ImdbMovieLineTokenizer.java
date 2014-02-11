package com.bazoud.springbatch.imdb.data.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.RegexLineTokenizer;
import org.springframework.beans.factory.InitializingBean;

public class ImdbMovieLineTokenizer extends RegexLineTokenizer implements InitializingBean {
  private static final Logger LOG = LoggerFactory.getLogger(ImdbMovieLineTokenizer.class);
  private String MOVIE_NAME_PATTERN = "\"(.+?)\"";

  private String RELEASE_DATE_PATTERN = "\\s+\\((?:(\\d{4}||\\?{4}))(?:\\/([IXV]+))?\\)";

  private String TV_PATTERN = "(?:\\s+\\(((?:V)||(?:TV)||(?:VG))\\))?";

  // private String EPISODE_PATTERN = "(?:\\s+\\{(.+?)?(?:\\s+\\((?:#(?:(\\d+?)\\.(\\d+?)))?(\\d{4}-\\d{2}-\\d{2})?\\))?\\})?";
  private String EPISODE_PATTERN = "(?:\\s+\\{([^(]+?)?\\s*(?:(?:\\(#(?:(\\d*)\\.(\\d*)\\)))?(?:(?:\\((\\d{4}-\\d{2}-\\d{2})\\)))?)?\\})?(?:\\s+\\{\\{(.+?)?\\}\\})?";

  private String MOVIE_STATE = "(?:\\s+\\{\\{(.+?)?\\}\\})?";

  private String BROADCAST_DATE_PATTERN = "(?:\\s+(\\d{4}||\\?{4})(?:\\-(?:(\\d{4}||\\?{4})))?)";

  @Override
  public void afterPropertiesSet() throws Exception {
    // "Title" (release) (type) {episode title (#season number.episode number) (episode date)} (year-year) (comment)
    String pattern = "^"
        + MOVIE_NAME_PATTERN
        + RELEASE_DATE_PATTERN
        + TV_PATTERN
        + EPISODE_PATTERN
        + MOVIE_STATE
        + BROADCAST_DATE_PATTERN
        + "$";
    LOG.info(pattern);
    setRegex(pattern);
  }
}
