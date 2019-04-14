package org.bonitasoft.engine.spring;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import org.bonitasoft.engine.bpm.bar.BusinessArchive;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.dsl.process.Process;
import org.bonitasoft.engine.test.TestEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BonitaProcessContainer {

    private TestEngine engine;
    private BonitaEngine bonitaEngine;
    private List<Process> processes;
    private List<BonitaProcessBuilder> bonitaProcessBuilders;
    private List<ProcessDefinition> processDefinitions;

    @Autowired
    public BonitaProcessContainer(TestEngine engine,
                                  BonitaEngine bonitaEngine,
                                  List<Process> processes,
                                  List<BonitaProcessBuilder> bonitaProcessBuilders) {
        this.engine = engine;
        this.bonitaEngine = bonitaEngine;
        this.processes = processes;
        this.bonitaProcessBuilders = bonitaProcessBuilders;
    }

    //TODO should use application event
    @PostConstruct
    private void startEngineEndDeploy() throws Exception {
        engine.start();


        List<BusinessArchive> exportedProcesses;
        exportedProcesses = processes.stream().map(Process::export).collect(Collectors.toList());
        List<BusinessArchive> processesFromComponents = bonitaProcessBuilders.stream().map(BonitaProcessBuilder::build).map(Process::export).collect(Collectors.toList());
        exportedProcesses.addAll(processesFromComponents);
        processDefinitions = bonitaEngine.deployProcesses(exportedProcesses);
    }


}
