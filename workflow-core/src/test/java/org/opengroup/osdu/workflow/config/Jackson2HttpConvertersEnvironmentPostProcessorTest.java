package org.opengroup.osdu.workflow.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.opengroup.osdu.workflow.config.Jackson2HttpConvertersEnvironmentPostProcessor.PREFERRED_JSON_MAPPER;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;

class Jackson2HttpConvertersEnvironmentPostProcessorTest {

  private final Jackson2HttpConvertersEnvironmentPostProcessor processor =
      new Jackson2HttpConvertersEnvironmentPostProcessor();

  @Test
  void shouldDefaultToJackson2() {
    StandardEnvironment environment = new StandardEnvironment();
    processor.postProcessEnvironment(environment, new SpringApplication());
    assertEquals("jackson2", environment.getProperty(PREFERRED_JSON_MAPPER));
  }

  @Test
  void shouldNotOverrideExplicitApplicationProperty() {
    StandardEnvironment environment = new StandardEnvironment();
    environment.getPropertySources().addFirst(
        new MapPropertySource("application", Map.of(PREFERRED_JSON_MAPPER, "jackson")));
    processor.postProcessEnvironment(environment, new SpringApplication());
    assertEquals("jackson", environment.getProperty(PREFERRED_JSON_MAPPER));
  }
}
