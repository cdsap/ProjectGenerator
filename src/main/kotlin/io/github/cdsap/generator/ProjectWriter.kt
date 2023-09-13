package io.github.cdsap.generator

import io.github.cdsap.generator.files.*
import io.github.cdsap.generator.model.LanguageAttributes
import io.github.cdsap.generator.model.ProjectGraph
import io.github.cdsap.generator.model.Versions
import io.github.cdsap.generator.writer.ConventionPluginWriter
import io.github.cdsap.generator.writer.ModulesWriter
import java.io.File

class ProjectWriter(
    private val nodes: List<ProjectGraph>,
    private val languages: List<LanguageAttributes>,
    private val numberOfClassPerModule: Int,
    private val versions: Versions
) {
    fun write() {
        println("Creating Convention Plugin files")
        ConventionPluginWriter(languages, versions).write()
        println("Creating Modules files")
        ModulesWriter(nodes, languages, numberOfClassPerModule).write()

        println("Creating Project files")
        createGradleProperties(languages)
        copyGradleWrapper(languages)
        writeSettingsGradle(nodes, languages)
        createProjectBuildGradle(languages)
    }

    private fun copyGradleWrapper(languages: List<LanguageAttributes>) {
        languages.forEach {
            if (!File("${it.projectName}/gradlew").exists()) {
                File("gradlew").copyTo(File("${it.projectName}/gradlew")).setExecutable(true)
                File("gradle").copyRecursively(File("${it.projectName}/gradle"))
            }
        }
    }

    private fun createProjectBuildGradle(languages: List<LanguageAttributes>) {
        val plugins = BuildGradle().get(versions)
        languages.forEach {
            File("${it.projectName}/build.${it.extension}").createNewFile()
            File("${it.projectName}/build.${it.extension}").writeText(plugins)
        }
    }

    private fun writeSettingsGradle(nodes: List<ProjectGraph>, languages: List<LanguageAttributes>) {
        var settingsGradleContent = SettingsGradle().get()

        nodes.forEach {
            settingsGradleContent += "\ninclude (\":layer_${it.layer}:${it.id}\")"
        }
        languages.forEach {
            File("${it.projectName}/settings.${it.extension}").createNewFile()
            File("${it.projectName}/settings.${it.extension}").writeText(settingsGradleContent)
        }
    }

    private fun createGradleProperties(languages: List<LanguageAttributes>) {
        val gradleProperties = GradleProperties().get()
        languages.forEach {
            File("${it.projectName}/gradle.properties").createNewFile()
            File("${it.projectName}/gradle.properties").writeText(gradleProperties)
        }
    }
}


