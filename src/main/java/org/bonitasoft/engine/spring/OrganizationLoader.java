package org.bonitasoft.engine.spring;

import java.io.InputStream;
import javax.annotation.PostConstruct;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.commons.io.IOUtil;
import org.bonitasoft.engine.identity.ImportPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@ConditionalOnResource(resources = "classpath:organization.xml")
public class OrganizationLoader {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationLoader.class);

    private APIClient apiClient;
    private BonitaEngine bonitaEngine;

    @Autowired
    public OrganizationLoader(APIClient apiClient, BonitaEngine bonitaEngine) {
        this.apiClient = apiClient;
        this.bonitaEngine = bonitaEngine;
    }

    @PostConstruct
    public void importOrganization() throws Exception {
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/organization.xml")) {
            if (resourceAsStream == null) {
                logger.info("No organization.xml detected in classpath. Skipping organization import");
                return;
            }
            logger.info("Importing organization.xml from classpath");
            String content = IOUtil.read(resourceAsStream);
            apiClient.getIdentityAPI().importOrganizationWithWarnings(content, ImportPolicy.MERGE_DUPLICATES);
        }
    }
}
