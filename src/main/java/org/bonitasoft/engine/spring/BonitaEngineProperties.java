package org.bonitasoft.engine.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "org.bonitasoft.engine")
public class BonitaEngineProperties implements InitializingBean {

//TODO put properties here instead of in the local-server.xml

    String h2Dir;

    public String getH2Dir() {
        return h2Dir;
    }

    public void setH2Dir(String h2Dir) {
        this.h2Dir = h2Dir;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("set h2 dir from autoconfigure");
        System.setProperty("org.bonitasoft.h2.database.dir", h2Dir != null ? h2Dir : "./build/h2Dir");
    }
}
