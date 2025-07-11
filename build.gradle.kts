import com.github.gradle.node.npm.task.NpmTask
import org.gradle.kotlin.dsl.register

plugins {
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.node-gradle.node") version "7.0.1"

    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
}

group = "com.test"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

val buildEnv = System.getProperty("build.env") ?: "local"

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-web")

    // jdbc
    runtimeOnly("org.postgresql:postgresql")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
    implementation("org.springdoc:springdoc-openapi-starter-common:2.8.6")

    // logging
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")

    // exposed
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.61.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.61.0")
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.61.0")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

node {
    version.set("22.9.0")
    npmVersion.set("10.2.2")
    npmInstallCommand.set("install")
    distBaseUrl.set("https://nodejs.org/dist")
    download.set(true)
    workDir.set(file("${project.projectDir}/frontend/.cache/nodejs"))
    npmWorkDir.set(file("${project.projectDir}/frontend/.cache/npm"))
    nodeProjectDir.set(file("${project.projectDir}/frontend"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<Delete>("deleteVueArtifact") {
    delete("${project.projectDir}/src/main/resources/static")
}

tasks.register<NpmTask>("npmBuild") {
    dependsOn("deleteVueArtifact", tasks.npmInstall)
    println("buildEnv = $buildEnv")
    npmCommand.set(listOf("run", "build:$buildEnv"))
}

tasks.clean {
    dependsOn("deleteVueArtifact")
}

tasks.processResources {
    if (buildEnv != "local") {
        dependsOn("npmBuild")
    }
}

tasks.bootJar {
    archiveFileName.set("app.jar")
}
