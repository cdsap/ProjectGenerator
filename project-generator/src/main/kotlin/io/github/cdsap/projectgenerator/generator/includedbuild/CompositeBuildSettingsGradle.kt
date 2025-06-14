package io.github.cdsap.projectgenerator.generator.includedbuild

class CompositeBuildSettingsGradle {
    fun get() = """
        |dependencyResolutionManagement {
        |    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        |    repositories {
        |        google()
        |        mavenCentral()
        |        gradlePluginPortal()
        |        versionCatalogs {
        |            create("libs") {
        |                from(files("../gradle/libs.versions.toml"))
        |            }
        |        }
        |    }
        |}
        |rootProject.name = "build-logic"
        |include(":convention")
    """.trimMargin()
}
