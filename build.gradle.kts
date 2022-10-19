plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    kotlin("plugin.jpa") version "1.3.72"

    id("io.quarkus")
    id("org.jetbrains.kotlin.plugin.noarg") version "1.4.10"
    id("org.liquibase.gradle") version "2.0.4"

}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation("io.quarkus:quarkus-kubernetes")
    implementation("io.quarkus:quarkus-container-image-jib")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-vertx")
    implementation("org.jobrunr:quarkus-jobrunr:5.1.7")
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-hibernate-reactive-rest-data-panache")
    implementation("io.quarkus:quarkus-mongodb-rest-data-panache")
    implementation("io.quarkus:quarkus-liquibase-mongodb")
    implementation("io.quarkus:quarkus-mongodb-client")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.smallrye.reactive:mutiny-kotlin:1.7.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.commons:commons-csv:1.9.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.kotest:kotest-assertions-core:5.5.0")
    testImplementation("io.mockk:mockk:1.13.2")
    liquibaseRuntime("org.liquibase:liquibase-core:4.16.1")
    liquibaseRuntime("org.liquibase.ext:liquibase-mongodb:4.16.1")


}

group = "com.itsz"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "logLevel" to "info",
            "changeLogFile" to "src/main/resources/db/changeLog.xml",
            "url" to "mongodb://localhost:27017/users",
            "driver" to "liquibase.ext.mongodb.database.MongoClientDriver"
        )
    }
    runList = "main"
}

tasks.register("deploy") {
    doFirst {
        liquibase {
            activities.register("main") {
                this.arguments = mapOf(
                    "changelog-file" to "System.properties.liquibaseChangeLogFile",
                    "contexts" to "System.properties.liquibaseContexts"
                )
            }
        }
    }
}







