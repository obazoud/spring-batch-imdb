package com.bazoud.springbatch.imdb.xml.domain;

import com.google.common.base.Joiner;

import java.util.List;

public class Movie {
  private int id;
  private String name;
  private int productionYear;
  private List<String> directors;

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

  public String getRawDirectors() {
    return Joiner.on(",").join(getDirectors());
  }
}
