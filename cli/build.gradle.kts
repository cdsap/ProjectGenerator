plugins {
    kotlin("jvm") version "2.2.10"
    application
    id("io.github.cdsap.fatbinary") version "1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.github.ajalt.clikt:clikt:4.4.0")
    implementation(project(":project-generator"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.20.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.20.0")
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}


application {
    mainClass.set("MainKt")
}

fatBinary {
    mainClass = "io.github.cdsap.projectgenerator.cli.Main"
    name = "projectGenerator"
}
