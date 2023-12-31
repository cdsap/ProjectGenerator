package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.files.*
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions
import java.io.File

class ConventionPluginWriter(
    private val languages: List<LanguageAttributes>,
    private val versions: Versions,
    private val requested: TypeProjectRequested
) {

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
            File("${it.projectName}/build-logic/convention/build.gradle.kts").writeText(
                CompositeBuildBuildGradle().get(versions, requested)
            )
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
        createPluginJvm(languages)
        if (requested == TypeProjectRequested.ANDROID) {
            createPluginAndroid(languages)
        }
    }

    private fun createPluginAndroid(languages: List<LanguageAttributes>) {
        val pluginAndroidLib = CompositeBuildPluginAndroidLib().get()
        val pluginAndroidApp = CompositeBuildPluginAndroidApp().get()
        languages.forEach {
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidApp.kt").createNewFile()
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidApp.kt").writeText(
                pluginAndroidApp
            )
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidLib.kt").createNewFile()
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidLib.kt").writeText(
                pluginAndroidLib
            )
        }
    }

    private fun createPluginJvm(languages: List<LanguageAttributes>) {
        val plugin = CompositeBuildPlugin1().get()
        languages.forEach {
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/Plugin1.kt").createNewFile()
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/Plugin1.kt").writeText(plugin)
        }

    }
}

