package org.bonitasoft.engine.spring;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.bpm.bar.BusinessArchive;
import org.bonitasoft.engine.bpm.process.ActivationState;
import org.bonitasoft.engine.bpm.process.Problem;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessDeployException;
import org.bonitasoft.engine.bpm.process.ProcessDeploymentInfo;
import org.bonitasoft.engine.bpm.process.ProcessEnablementException;
import org.bonitasoft.engine.dsl.process.BARBuilder;
import org.bonitasoft.engine.dsl.process.Process;
import org.bonitasoft.engine.dsl.process.ProcessConfiguration;
import org.bonitasoft.engine.exception.AlreadyExistsException;
import org.bonitasoft.engine.exception.DeletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BonitaProcessContainer {

    private static final Logger logger = LoggerFactory.getLogger(BonitaProcessContainer.class);
    private BonitaEngine bonitaEngine;
    private APIClient apiClient;
    private List<BonitaProcessBuilder> bonitaProcessBuilders;
    private List<ProcessDefinition> processDefinitions = new ArrayList<>();

    @Autowired
    public BonitaProcessContainer(BonitaEngine bonitaEngine,
                                  APIClient apiClient,
                                  List<BonitaProcessBuilder> bonitaProcessBuilders) {
        this.bonitaEngine = bonitaEngine;
        this.apiClient = apiClient;
        this.bonitaProcessBuilders = bonitaProcessBuilders;
    }

    //TODO should use application event
    @PostConstruct
    private void deployProcesses() throws Exception {
        for (BonitaProcessBuilder bonitaProcessBuilder : bonitaProcessBuilders) {

            deploy(bonitaProcessBuilder);
        }
    }

    private ProcessDefinition deploy(BonitaProcessBuilder bonitaProcessBuilder) throws ProcessDeployException, ProcessDefinitionNotFoundException, DeletionException, ProcessActivationException, AlreadyExistsException {
        Process process = bonitaProcessBuilder.build();
        ProcessConfiguration configuration = bonitaProcessBuilder.configuration();
        BusinessArchive bar = BARBuilder.INSTANCE.build(process, configuration == null ? new ProcessConfiguration() : configuration);
        ProcessDefinition processDefinition;
        try {
            processDefinition = apiClient.getProcessAPI().deploy(bar);
        } catch (AlreadyExistsException e) {
            logger.info("Process {} {} is already deployed, redeploying it", getProcessName(bar), getProcessVersion(bar) );
            long processDefinitionId = apiClient.getProcessAPI().getProcessDefinitionId(getProcessName(bar), getProcessVersion(bar));
            apiClient.getProcessAPI().disableAndDeleteProcessDefinition(processDefinitionId);
            processDefinition = apiClient.getProcessAPI().deploy(bar);
        }
        try {
            ProcessDeploymentInfo processDeploymentInfo = apiClient.getProcessAPI().getProcessDeploymentInfo(processDefinition.getId());
            if (processDeploymentInfo.getActivationState() == ActivationState.DISABLED) {
                apiClient.getProcessAPI().enableProcess(processDefinition.getId());
            }
        } catch (ProcessEnablementException e) {
            List<Problem> processResolutionProblems = apiClient.getProcessAPI().getProcessResolutionProblems(processDefinition.getId());
            logger.error("Unable to enable process {} {} because:", getProcessName(bar), getProcessVersion(bar));
            for (Problem problem : processResolutionProblems) {
                logger.error(problem.getDescription());
            }
        }
        processDefinitions.add(processDefinition);
        return processDefinition;
    }

    private String getProcessVersion(BusinessArchive bar) {
        return bar.getProcessDefinition().getVersion();
    }

    private String getProcessName(BusinessArchive bar) {
        return bar.getProcessDefinition().getName();
    }


}
