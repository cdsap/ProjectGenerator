package io.github.cdsap.projectgenerator.files

class SettingsGradle {

    fun get(develocity: Boolean) : String {
        val develocityBlock = if(develocity) {
            """
                |plugins {
                |    id("com.gradle.develocity") version "4.0.1"
                |    id("com.gradle.common-custom-user-data-gradle-plugin") version "2.2"
                |}
                |
                |develocity {
                |    server = "https://ge.solutions-team.gradle.com/"
                |    allowUntrustedServer = true
                |    buildScan {
                |        uploadInBackground.set(false)
                |    }
                |}
            """.trimMargin()
        } else ""

        return """
            |pluginManagement {
            |    includeBuild("build-logic")
            |    repositories {
            |        google()
            |        mavenCentral()
            |        gradlePluginPortal()
            |    }
            |}
            |rootProject.name="project"
            |$develocityBlock
            |dependencyResolutionManagement {
            |    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
            |    repositories {
            |        google()
            |        mavenCentral()
            |    }
            |}
        """.trimMargin()
    }
}
