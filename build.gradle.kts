plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    jcenter()
    maven("http://repositories.rd.lan/maven/all/")
}

group = "org.bonitasoft.engine"

dependencies {
    api("org.bonitasoft.engine:bonita-client:7.8.3")
    api("org.bonitasoft.engine:bonita-common:7.8.3")
    api("org.bonitasoft.engine:bonita-server:7.8.3")
    api("org.bonitasoft.engine:bonita-test-api:7.8.3")
    api("org.bonitasoft.platform:platform-resources:7.8.3")
    api("org.springframework.boot:spring-boot-starter-jdbc:2.1.4.RELEASE")
    //should not depend on the DSL: interface of process should be in an other module
    implementation("org.bonitasoft.engine.dsl:process-kotlin-dsl:0.0.1")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.1.4.RELEASE")

    testImplementation("junit:junit:4.12")
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
}