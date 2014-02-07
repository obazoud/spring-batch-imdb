package com.bazoud.springbatch.imdb.sql.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class Movie {
  private int id;
  private String name;
  private int productionYear;
  private List<String> directors;
  private List<String> writers;
  private List<String> countries;
  private List<String> languages;
  private List<Actor> casting;
  private String summary;
  private List<String> genres;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getProductionYear() {
    return productionYear;
  }

  public void setProductionYear(int productionYear) {
    this.productionYear = productionYear;
  }

  public List<String> getDirectors() {
    return directors;
  }

  public void setDirectors(List<String> directors) {
    this.directors = directors;
  }

  public List<String> getWriters() {
    return writers;
  }

  public void setWriters(List<String> writers) {
    this.writers = writers;
  }

  public List<String> getCountries() {
    return countries;
  }

  public void setCountries(List<String> countries) {
    this.countries = countries;
  }

  public List<String> getLanguages() {
    return languages;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public List<Actor> getCasting() {
    return casting;
  }

  public void setCasting(List<Actor> casting) {
    this.casting = casting;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public List<String> getGenres() {
    return genres;
  }

  public void setGenres(List<String> genres) {
    this.genres = genres;
  }
}
