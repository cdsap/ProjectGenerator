package io.github.cdsap.projectgenerator.files

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
