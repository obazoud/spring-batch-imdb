DROP TABLE IF EXISTS MOVIE;
CREATE TABLE MOVIE (
  ID               INT,
  NAME             TEXT   NOT NULL,
  PRODUCTION_YEAR  INT NOT NULL,
  DIRECTORS        TEXT,
  PRIMARY KEY(ID)
);
