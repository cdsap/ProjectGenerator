package io.github.cdsap.generator.files

class SettingsGradle {

    fun get() = """
            pluginManagement {
                includeBuild("build-logic")
                repositories {
                    google()
                    mavenCentral()
                    gradlePluginPortal()
                }
            }
            plugins {
                id("com.gradle.common-custom-user-data-gradle-plugin") version "1.8.1"
                id("com.gradle.enterprise") version "3.13.4"
            }
            gradleEnterprise {
                server = "http://ge.solutions-team.gradle.com"
                allowUntrustedServer = true
                buildScan {
                    publishAlways()
                }
            }
            rootProject.name="project"
            dependencyResolutionManagement {
                repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
                repositories {
                    google()
                    mavenCentral()
                }
            }


        """.trimIndent()
}
