package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.files.*
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.ConventionPluginWriter
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import io.github.cdsap.projectgenerator.generator.modules.AndroidModulesWriter
import io.github.cdsap.projectgenerator.generator.modules.JvmModulesWriter
import io.github.cdsap.projectgenerator.generator.toml.AndroidToml
import java.io.File

class ProjectWriter(
    private val nodes: List<ProjectGraph>,
    private val languages: List<LanguageAttributes>,
    private val versions: Versions,
    private val typeOfProjectRequested: TypeProjectRequested,
    private val typeOfStringResources: TypeOfStringResources,
    private val generateUnitTest: Boolean,
    private val gradle: GradleWrapper,
    private val develocity: Boolean
) {
    fun write() {
        println("Creating Convention Plugin files")
        ConventionPluginWriter(languages, versions, typeOfProjectRequested).write()
        println("Creating Modules files")

        when(typeOfProjectRequested) {
            TypeProjectRequested.ANDROID -> AndroidModulesWriter(nodes, languages, typeOfStringResources, generateUnitTest,versions).write()
            TypeProjectRequested.JVM -> JvmModulesWriter(nodes, languages, generateUnitTest,versions).write()
        }

        println("Creating Project files")
        createGradleProperties(languages)
        copyGradleWrapper(gradle, languages)
        createToml(languages)
        writeSettingsGradle(nodes, languages,versions, develocity)
        createProjectBuildGradle(languages)
    }

    private fun createToml(languages: List<LanguageAttributes>) {
        val toml = AndroidToml().toml(versions)
        languages.forEach {
            File("${it.projectName}/gradle/libs.versions.toml").projectFile(toml)
        }
    }

    private fun copyGradleWrapper(gradle: GradleWrapper, languages: List<LanguageAttributes>) {
        languages.forEach {
            gradle.installGradleVersion(it.projectName)
        }
    }

    private fun createProjectBuildGradle(
        languages: List<LanguageAttributes>
    ) {
        val plugins = if (typeOfProjectRequested == TypeProjectRequested.JVM) BuildGradle().getJvm(versions)
        else BuildGradle().getAndroid(versions)
        languages.forEach {
            File("${it.projectName}/build.${it.extension}").projectFile(plugins)
        }
    }

    private fun writeSettingsGradle(nodes: List<ProjectGraph>, languages: List<LanguageAttributes>, versions: Versions, develocity: Boolean) {
        var settingsGradleContent = SettingsGradle().get(versions, develocity)

        nodes.forEach {
            settingsGradleContent += "\ninclude (\":layer_${it.layer}:${it.id}\")"
        }
        languages.forEach {
            File("${it.projectName}/settings.${it.extension}").projectFile(settingsGradleContent)
        }
    }

    private fun createGradleProperties(languages: List<LanguageAttributes>) {
        val gradleProperties = GradleProperties().get(versions)
        languages.forEach {
            File("${it.projectName}/gradle.properties").projectFile(gradleProperties)
        }
    }
}
