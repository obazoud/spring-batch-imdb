package com.bazoud.springbatch.imdb.data.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.RegexLineTokenizer;

public class ImdbMovieLineTokenizer extends RegexLineTokenizer {
  private static final Logger LOG = LoggerFactory.getLogger(ImdbMovieLineTokenizer.class);

  private final String MOVIE_NAME_PATTERN = "\"(.+?)\"";

  private final String RELEASE_DATE_PATTERN = "\\s+\\((?:(\\d{4}||\\?{4}))(?:\\/([IXV]+))?\\)";

  private final String TV_PATTERN = "(?:\\s+\\(((?:V)||(?:TV)||(?:VG))\\))?";

  private final String EPISODE_PATTERN = "(?:\\s+\\{([^(]+?)?\\s*(?:(?:\\(#(?:(\\d*)\\.(\\d*)\\)))?(?:(?:\\((\\d{4}-\\d{2}-\\d{2})\\)))?)?\\})?";

  private final String MOVIE_STATE = "(?:\\s+\\{\\{(.+?)?\\}\\})?";

  private final String BROADCAST_DATE_PATTERN = "(?:\\s+(\\d{4}||\\?{4})(?:\\-(?:(\\d{4}||\\?{4})))?)";

  // "Title" (releaseDate/stuff) (type) {episodeTitle (#episodeSeason.episodeNumber) (episodeDate)} {{state}} (broadcastDateBegin-broadcastDateEnd)
  private final String PATTERN = "^"
      + MOVIE_NAME_PATTERN
      + RELEASE_DATE_PATTERN
      + TV_PATTERN
      + EPISODE_PATTERN
      + MOVIE_STATE
      + BROADCAST_DATE_PATTERN
      + "$";

  public ImdbMovieLineTokenizer() {
    LOG.debug(PATTERN);
    setRegex(PATTERN);
    setNames(new String[] {
        "title",
        "releaseDate",
        "stuff",
        "type",
        "episodeTitle",
        "episodeSeason",
        "episodeNumber",
        "episodeDate",
        "state",
        "broadcastDateBegin",
        "broadcastDateEnd"
    });
  }

  @Override
  public String toString() {
    return "http://www.regexper.com/#" + PATTERN;
  }
}
