package org.bonitasoft.engine;

import static org.assertj.core.api.Assertions.assertThat;

import org.bonitasoft.engine.spring.BonitaEngine;
import org.bonitasoft.engine.spring.BonitaEngineAutoConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

public class BonitaEngineAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(BonitaEngineAutoConfiguration.class));


    @Test
    @Ignore
    public void should_create_engine() {
        this.contextRunner
                .run((context) -> {
                    assertThat(context).hasSingleBean(BonitaEngine.class);
                });
    }
}
