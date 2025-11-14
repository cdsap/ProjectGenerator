plugins {
    alias(libs.plugins.kotlin.jvm)
    `maven-publish`
    `signing`
    alias(libs.plugins.maven.publish)
}

group = "io.github.cdsap"
version = "0.3.7"

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(gradleTestKit())
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}

// Separate task for unit tests (excluding E2E tests)
tasks.register<Test>("unitTest") {
    group = "verification"
    description = "Runs unit tests excluding E2E tests"
    useJUnitPlatform()
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    filter {
        excludeTestsMatching("*E2E*")
    }


}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("io.github.cdsap", "projectgenerator", "0.3.7")

    pom {
        scm {
            connection.set("scm:git:git://github.com/cdsap/ProjectGenerator/")
            url.set("https://github.com/cdsap/ProjectGenerator/")
        }
        name.set("projectgenerator")
        url.set("https://github.com/cdsap/ProjectGenerator/")
        description.set(
            "Android and JVM Gradle Projects generator"
        )
        licenses {
            license {
                name.set("The MIT License (MIT)")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("cdsap")
                name.set("Inaki Villar")
            }
        }
    }
}


if (extra.has("signing.keyId")) {
    afterEvaluate {
        configure<SigningExtension> {
            (
                extensions.getByName("publishing") as
                    PublishingExtension
                ).publications.forEach {
                    sign(it)
                }
        }
    }
}
