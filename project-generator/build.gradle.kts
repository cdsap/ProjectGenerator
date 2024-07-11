plugins {
    kotlin("jvm") version "1.9.24"
    `maven-publish`
    `signing`
}

group = "io.github.cdsap"
version = "0.1.6"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

configure<JavaPluginExtension> {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = "Snapshots"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

            credentials {
                username = System.getenv("USERNAME_SNAPSHOT")
                password = System.getenv("PASSWORD_SNAPSHOT")
            }
        }
        maven {
            name = "Release"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = System.getenv("USERNAME_SNAPSHOT")
                password = System.getenv("PASSWORD_SNAPSHOT")
            }
        }
    }
    publications {
        create<MavenPublication>("libPublication") {
            from(components["java"])
            artifactId = "projectgenerator"
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                scm {
                    connection.set("scm:git:git://github.com/cdsap/ProjectGenerator/")
                    url.set("https://github.com/cdsap/ProjectGenerator/")
                }
                name.set("projectgenerator")
                url.set("https://github.com/cdsap/ProjectGenerator/")
                description.set(
                    "Returns graph metrics by project"
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
