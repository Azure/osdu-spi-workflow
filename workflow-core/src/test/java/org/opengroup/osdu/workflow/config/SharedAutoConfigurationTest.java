package org.opengroup.osdu.workflow.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.jackson2.autoconfigure.Jackson2AutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class SharedAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(
          JacksonAutoConfiguration.class, Jackson2AutoConfiguration.class))
      .withUserConfiguration(SharedAutoConfiguration.class);

  @Test
  void sharedJacksonPropertiesShouldBindAndApplyToJackson2Mapper() {
    contextRunner.run(context -> {
      assertThat(context).hasNotFailed();
      ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
      assertThat(objectMapper.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)).isTrue();
      assertThat(objectMapper.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)).isFalse();
    });
  }
}
