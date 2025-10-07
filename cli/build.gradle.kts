plugins {
    alias(libs.plugins.kotlin.jvm)
    application
    alias(libs.plugins.fatbinary)
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.clikt)
    implementation(project(":project-generator"))
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.dataformat.yaml)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
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
