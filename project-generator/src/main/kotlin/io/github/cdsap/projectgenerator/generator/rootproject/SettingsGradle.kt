package io.github.cdsap.projectgenerator.generator.rootproject

import io.github.cdsap.projectgenerator.model.Versions

class SettingsGradle {

    fun get(versions: Versions, develocity: Boolean, projectName: String): String {
        val pluginLines = buildList {
            if (develocity) {
                add("""id("com.gradle.develocity") version "${versions.project.develocity}"""")
                add("""id("com.gradle.common-custom-user-data-gradle-plugin") version "${versions.project.ccud}"""")
            }
            addAll(
                versions.additionalSettingsPlugins.map {
                    """id("${it.id}") version "${it.version}""""
                }
            )
        }

        val pluginsBlock = if (pluginLines.isNotEmpty()) {
            """
            |plugins {
            |    ${pluginLines.joinToString("\n    ")}
            |}
            """.trimMargin()
        } else {
            ""
        }

        val develocityConfig = if (develocity) {
            develocityBlock(versions.project.develocityUrl)
        } else {
            ""
        }

        return """
            |pluginManagement {
            |    includeBuild("build-logic")
            |    repositories {
            |        google()
            |        mavenCentral()
            |        gradlePluginPortal()
            |    }
            |}
            |$pluginsBlock
            |rootProject.name = "$projectName"
            |$develocityConfig
            |dependencyResolutionManagement {
            |    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
            |    repositories {
            |        google()
            |        mavenCentral()
            |    }
            |}
        """.trimMargin()
    }

    private fun develocityBlock(develocityUrl: String): String {
        return if (develocityUrl.isBlank()) {
            """
            |develocity {
            |    buildScan {
            |        termsOfUseUrl = "https://gradle.com/terms-of-service"
            |        termsOfUseAgree = "yes"
            |        uploadInBackground.set(false)
            |    }
            |}
            """.trimMargin()
        } else {
            """
            |develocity {
            |    server = "$develocityUrl"
            |    allowUntrustedServer = true
            |    buildScan {
            |        uploadInBackground.set(false)
            |    }
            |}
            """.trimMargin()
        }
    }
}
