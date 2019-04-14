package org.bonitasoft.engine.spring;


import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.test.TestEngine;
import org.bonitasoft.engine.test.TestEngineImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ TestEngine.class })
@EnableConfigurationProperties(BonitaEngineProperties.class)
public class BonitaEngineAutoConfiguration {

    @Bean
    TestEngine testEngine() {
        TestEngine instance = TestEngineImpl.getInstance();
        instance.setDropOnStart(false);
        instance.setDropOnStop(false);
        return instance;
    }

    @Bean
    APIClient apiAccessor() {
        return new APIClient();
    }


    @Configuration
    @ComponentScan
    @ConditionalOnMissingBean({ BonitaEngine.class })
    protected static class BonitaEngineConfiguration {

    }



}
