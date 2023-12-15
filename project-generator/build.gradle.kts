plugins {
    kotlin("jvm") version "1.9.20"
}

group = "io.github.cdsap"
version = "0.1.0"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
