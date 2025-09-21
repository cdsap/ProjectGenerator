plugins {
    kotlin("jvm") version "2.2.20"
    `maven-publish`
    `signing`
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "io.github.cdsap"
version = "0.3.4"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(gradleTestKit())
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("io.github.cdsap", "projectgenerator", "0.3.4")

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
