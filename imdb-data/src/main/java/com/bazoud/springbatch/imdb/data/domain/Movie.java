package com.bazoud.springbatch.imdb.data.domain;

import lombok.Data;

@Data
public class Movie {
  private String title;
  private String releaseDate;
  private String stuff;
  private String type;
  private String episodeTitle;
  private String episodeSeason;
  private String episodeNumber;
  private String episodeDate;
  private String state;
  private String broadcastDateBegin;
  private String broadcastDateEnd;
}
