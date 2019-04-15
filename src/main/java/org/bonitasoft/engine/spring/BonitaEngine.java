package org.bonitasoft.engine.spring;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.bpm.bar.BusinessArchive;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.test.TestEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;

@Component
@AutoConfigureBefore(OrganizationLoader.class)
public class BonitaEngine {

    private TestEngine engine;
    private APIClient apiClient;

    @Autowired
    public BonitaEngine(TestEngine engine, APIClient apiClient) {
        this.engine = engine;
        this.apiClient = apiClient;
    }

    @PostConstruct
    public void start() throws Exception {
        engine.start();
        apiClient.login("install", "install");
    }
}
