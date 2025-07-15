plugins {
    kotlin("jvm") version "2.2.0"
    `maven-publish`
    `signing`
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "io.github.cdsap"
version = "0.2.1"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(gradleTestKit())
}

tasks.test {
    useJUnitPlatform()
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("io.github.cdsap", "projectgenerator", "0.2.1")

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
