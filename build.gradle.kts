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
version = "0.0.1"

val bonitaBundle by configurations.creating

dependencies {
    api("org.bonitasoft.engine:bonita-client:7.9.0")
    api("org.bonitasoft.engine:bonita-common:7.9.0")
    api("org.bonitasoft.engine:bonita-server:7.9.0")
    api("org.bonitasoft.engine:bonita-test-api:7.9.0")
    api("org.bonitasoft.platform:platform-setup:7.9.0")
    api("org.springframework.boot:spring-boot-starter:2.1.4.RELEASE")
    api("org.springframework.boot:spring-boot-starter-data-rest:2.1.4.RELEASE")
    //should not depend on the DSL: interface of process should be in an other module
    implementation("org.bonitasoft.engine.dsl:process-kotlin-dsl:0.0.1")
    implementation("org.slf4j:slf4j-api:1.7.26")



    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.1.4.RELEASE")

    testImplementation("org.springframework.boot:spring-boot-test:2.1.4.RELEASE")
    testImplementation("junit:junit:4.12")
    testImplementation("org.assertj:assertj-core:3.12.2")
}




publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
}