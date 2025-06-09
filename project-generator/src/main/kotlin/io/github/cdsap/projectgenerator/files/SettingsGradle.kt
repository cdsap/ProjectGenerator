package io.github.cdsap.projectgenerator.files

import io.github.cdsap.projectgenerator.model.Versions

class SettingsGradle {

    fun get(versions: Versions, develocity: Boolean): String {
        val develocityBlock = if (develocity) {
            """
                |plugins {
                |    id("com.gradle.develocity") version "4.0.1"
                |    id("com.gradle.common-custom-user-data-gradle-plugin") version "2.2"
                |    ${additionalSettingsPlugins(versions)}
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

    fun additionalSettingsPlugins(versions: Versions): String {
        var additionalPlugins = ""
        versions.additionalSettingsPlugins.forEach {
            additionalPlugins += "id(\"${it.id}\") version \"${it.version}\"\n"
        }
        return additionalPlugins
    }
}
