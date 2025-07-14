package io.github.cdsap.projectgenerator.generator.rootproject

import io.github.cdsap.projectgenerator.model.Versions

class SettingsGradle {

    fun get(versions: Versions, develocity: Boolean, projectName: String): String {
        val develocityBlock = if (develocity) {
            """
                |plugins {
                |    id("com.gradle.develocity") version "${versions.project.develocity}"
                |    id("com.gradle.common-custom-user-data-gradle-plugin") version "${versions.project.ccud}"
                |    ${additionalSettingsPlugins(versions)}
                |}
                |
                |develocity {
                |    server = "${versions.project.develocityUrl}"
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
            |rootProject.name="$projectName"
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
