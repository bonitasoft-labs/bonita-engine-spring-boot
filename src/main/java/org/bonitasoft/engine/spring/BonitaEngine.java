package org.bonitasoft.engine.spring;

import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.bpm.bar.BusinessArchive;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BonitaEngine {

    private APIClient apiClient;

    @Autowired
    public BonitaEngine(APIClient apiClient) {
        System.out.println("creating bean: BonitaEngine");
        this.apiClient = apiClient;
    }

    public List<ProcessDefinition> deployProcesses(List<BusinessArchive> processes) throws Exception {
        //FIXME change default user using configuration
        apiClient.login("install","install");
        List<ProcessDefinition> deployedProcesses = new ArrayList<>();
        for (BusinessArchive process : processes) {
            deployedProcesses.add(apiClient.getProcessAPI().deployAndEnableProcess(process));
        }
        return deployedProcesses;
    }
}
