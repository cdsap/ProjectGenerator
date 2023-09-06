package io.github.cdsap.generator.files

class CompositeBuildSettingsGradle {
    fun get() = """
                dependencyResolutionManagement {
                    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
                    repositories {
                        google()
                        mavenCentral()
                    }
                }
                rootProject.name = "build-logic"
                include(":convention")
            """.trimIndent()
}
