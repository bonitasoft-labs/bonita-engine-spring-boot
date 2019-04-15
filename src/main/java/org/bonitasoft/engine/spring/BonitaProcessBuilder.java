package org.bonitasoft.engine.spring;

import org.bonitasoft.engine.dsl.process.Process;
import org.bonitasoft.engine.dsl.process.ProcessConfiguration;

public interface BonitaProcessBuilder {

    Process build();
    default ProcessConfiguration configuration(){
        return null;
    }
}
