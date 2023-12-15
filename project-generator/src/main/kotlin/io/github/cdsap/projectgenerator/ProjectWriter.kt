package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.files.*
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.ConventionPluginWriter
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import io.github.cdsap.projectgenerator.writer.ModulesWriter
import java.io.File

class ProjectWriter(
    private val nodes: List<ProjectGraph>,
    private val languages: List<LanguageAttributes>,
    private val versions: Versions,
    private val typeOfProjectRequested: TypeProjectRequested,
    private val typeOfStringResources: TypeOfStringResources,
    private val generateUnitTest: Boolean,
    private val gradle: GradleWrapper,
    private val dependencyPlugins: Boolean
) {
    fun write() {
        println("Creating Convention Plugin files")
        ConventionPluginWriter(languages, versions, typeOfProjectRequested).write()
        println("Creating Modules files")
        ModulesWriter(nodes, languages, typeOfStringResources, generateUnitTest).write()

        println("Creating Project files")
        createGradleProperties(languages)
        copyGradleWrapper(gradle, languages)
        writeSettingsGradle(nodes, languages)
        createProjectBuildGradle(languages, dependencyPlugins)
    }

    private fun copyGradleWrapper(gradle: GradleWrapper, languages: List<LanguageAttributes>) {
        languages.forEach {
            gradle.installGradleVersion(it.projectName)
        }
    }

    private fun createProjectBuildGradle(
        languages: List<LanguageAttributes>,
        dependencyPlugins: Boolean
    ) {
        val plugins = if (typeOfProjectRequested == TypeProjectRequested.JVM) BuildGradle().getJvm(versions)
        else BuildGradle().get(versions, dependencyPlugins)
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


