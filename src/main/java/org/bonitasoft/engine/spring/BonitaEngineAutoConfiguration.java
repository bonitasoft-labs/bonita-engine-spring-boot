package org.bonitasoft.engine.spring;


import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.internal.servlet.HttpAPIServlet;
import org.bonitasoft.engine.test.TestEngine;
import org.bonitasoft.engine.test.TestEngineImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@ConditionalOnClass({ TestEngine.class })
@EnableConfigurationProperties(BonitaEngineProperties.class)
public class BonitaEngineAutoConfiguration {

    @Bean
    TestEngine testEngine(BonitaEngineProperties properties) {
        TestEngine instance = TestEngineImpl.getInstance();
        instance.setDropOnStart(false);
        instance.setDropOnStop(false);
        return instance;
    }

    @Bean
    APIClient apiAccessor() {
        return new APIClient();
    }


    @Bean
    public ServletRegistrationBean bonitaEngineHttpServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean<>(new HttpAPIServlet(), "/bonita/serverAPI/*");
        bean.setLoadOnStartup(1);
        return bean;
    }

}
