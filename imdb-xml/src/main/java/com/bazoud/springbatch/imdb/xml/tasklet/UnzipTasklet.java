package com.bazoud.springbatch.imdb.xml.tasklet;

import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.core.io.ClassPathResource;

public class UnzipTasklet extends SystemCommandTasklet {

  @Override
  public void afterPropertiesSet() throws Exception {
    this.setCommand("/usr/bin/unzip -o " + new ClassPathResource("movies.zip").getFile().getAbsolutePath());
    this.setTimeout(60000L);
    this.setWorkingDirectory("/tmp");
    super.afterPropertiesSet();
  }
}
