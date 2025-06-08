package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.files.*
import io.github.cdsap.projectgenerator.files.plugins.android.CompositeBuildPluginAndroidApp
import io.github.cdsap.projectgenerator.files.plugins.android.CompositeBuildPluginAndroidLib
import io.github.cdsap.projectgenerator.files.plugins.jvm.CompositeBuildJvmLib
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
            File("${it.projectName}/build-logic/convention/build.gradle.kts").projectFile(
                CompositeBuildBuildGradle().get(
                    versions,
                    requested
                )
            )
        }
    }

    private fun createSettingsGradlePlugin(languages: List<LanguageAttributes>) {
        val settingsGradlePlugin = CompositeBuildSettingsGradle().get()
        languages.forEach {
            File("${it.projectName}/build-logic/settings.gradle.kts").projectFile(settingsGradlePlugin)
        }
    }

    private fun createPlugin(languages: List<LanguageAttributes>) {

        if (requested == TypeProjectRequested.ANDROID) {
            createPluginAndroid(languages)
        }
        if (requested == TypeProjectRequested.JVM) {
            createPluginJvm(languages)
        }
    }

    private fun createPluginAndroid(languages: List<LanguageAttributes>) {
        val pluginAndroidLib = CompositeBuildPluginAndroidLib().get(versions)
        val pluginAndroidApp = CompositeBuildPluginAndroidApp().get(versions)
        languages.forEach {
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidApp.kt").projectFile(
                pluginAndroidApp
            )
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidLib.kt").projectFile(
                pluginAndroidLib
            )
        }
    }

    // TODO: do we need to create the application plugin?
    private fun createPluginJvm(languages: List<LanguageAttributes>) {
        val plugin = CompositeBuildJvmLib().get(versions)
        languages.forEach {
            File("${it.projectName}/build-logic/convention/src/main/kotlin/com/logic/PluginJvmLib.kt").projectFile(plugin)

        }
    }
}

