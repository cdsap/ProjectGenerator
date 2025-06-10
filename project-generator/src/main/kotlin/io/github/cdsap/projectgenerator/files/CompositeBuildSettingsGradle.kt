package io.github.cdsap.projectgenerator.files

class CompositeBuildSettingsGradle {
    fun get() = """
        |dependencyResolutionManagement {
        |    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        |    repositories {
        |        google()
        |        mavenCentral()
        |        gradlePluginPortal()
        |    }
        |}
        |rootProject.name = "build-logic"
        |include(":convention")
    """.trimMargin()
}
