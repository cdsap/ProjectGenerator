package io.github.cdsap.generator.writer

import io.github.cdsap.generator.files.CompositeBuildBuildGradle
import io.github.cdsap.generator.files.CompositeBuildPlugin1
import io.github.cdsap.generator.files.CompositeBuildSettingsGradle
import io.github.cdsap.generator.model.LanguageAttributes
import java.io.File

class ConventionPluginWriter(private val languages: List<LanguageAttributes>) {

    fun write() {
        createConventionFolders(languages)
        createBuildGradlePlugin(languages)
        createSettingsGradlePlugin(languages)
        createPlugin(languages)
    }

    private fun createConventionFolders(languages: List<LanguageAttributes>) {
        languages.forEach {
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic").mkdirs()
        }
    }

    private fun createBuildGradlePlugin(languages: List<LanguageAttributes>) {
        languages.forEach {
            File("${it.projectName}/build-logic/convention/build.gradle.kts").createNewFile()
            File("${it.projectName}/build-logic/convention/build.gradle.kts").writeText(CompositeBuildBuildGradle().get())
        }
    }

    private fun createSettingsGradlePlugin(languages: List<LanguageAttributes>) {
        val settingsGradlePlugin = CompositeBuildSettingsGradle().get()
        languages.forEach {
            File("${it.projectName}/build-logic/settings.gradle.kts").createNewFile()
            File("${it.projectName}/build-logic/settings.gradle.kts").writeText(settingsGradlePlugin)
        }
    }

    private fun createPlugin(languages: List<LanguageAttributes>) {
        val plugin = CompositeBuildPlugin1().get()
        languages.forEach {
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/Plugin1.kt").createNewFile()
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/Plugin1.kt").writeText(plugin)
        }
    }
}
