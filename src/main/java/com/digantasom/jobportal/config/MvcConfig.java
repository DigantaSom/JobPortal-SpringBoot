package com.digantasom.jobportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
// this configuration class will map requests for '/photos' to serve files from a directory on our own file system
public class MvcConfig implements WebMvcConfigurer {
  private static final String UPLOAD_DIR = "photos";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // overriding the default implementation to setup a custom resource handler
    exposeDirectory(UPLOAD_DIR, registry);
  }

  /* Converts the uploadDir string to a Path.
     Maps requests starting with "/photos/**" to a file system location file:<absolute path to the photos directory>
     The ** will match on all sub-directories.
  */
  private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {
    Path path = Paths.get(uploadDir);
    registry.addResourceHandler("/" + uploadDir + "/**").addResourceLocations("file:" + path.toAbsolutePath() + "/");
  }
}
