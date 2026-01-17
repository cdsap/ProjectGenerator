package io.github.cdsap.projectgenerator.cli

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import io.github.cdsap.projectgenerator.ProjectGenerator
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import java.io.File
import kotlin.text.buildString

fun main(args: Array<String>) {
    ProjectReportCli()
        .subcommands(GenerateProjects(), GenerateYaml())
        .main(args)
}

class ProjectReportCli : CliktCommand() {
    override fun run() = Unit // Root doesn't do anything itself
}

class GenerateProjects : CliktCommand(name = "generate-project") {
    private val shape: String by option().choice(
        "rhombus", "triangle", "flat",
        "rectangle", "middle_bottleneck", "inverse_triangle"
    ).default("rectangle")
    private val language: String by option().choice("kts", "groovy", "both").default("kts")
    private val modules by option().int().required()
        .check("max number of projects 4000") { it in (layers + 1)..4000 }
    private val type by option().choice("android", "jvm").default("android")
    private val classesModule by option().int().default(5)
    private val classesModuleType: String by option().choice("fixed", "random").default("fixed")
    private val typeOfStringResources: String by option().choice("large", "normal").default("normal")
    private val layers by option().int().default(5)
    private val generateUnitTest by option().flag(default = false)
    private val gradle: String by option().choice(
        "gradle_8_13",
        "gradle_8_14_3",
        "gradle_9_0_0",
        "gradle_9_1_0",
        "gradle_9_2_0",
        "gradle_9_3_0",
    )
        .default("gradle_9_3_0")
    private val develocity by option().flag(default = false)
    private val versionsFile by option().file()
    private val projectName by option()
    private val develocityUrl by option()
    private val agp9 by option().flag(default = false)


    override fun run() {
        val typeOfProjectRequested = TypeProjectRequested.valueOf(type.uppercase())
        val shape = Shape.valueOf(shape.uppercase())
        val versions = getVersions(versionsFile, develocityUrl, agp9)
        val develocityEnabled = getDevelocityEnabled(develocity, develocityUrl)
        ProjectGenerator(
            modules,
            shape,
            Language.valueOf(language.uppercase()),
            typeOfProjectRequested,
            ClassesPerModule(ClassesPerModuleType.valueOf(classesModuleType.uppercase()), classesModule),
            versions = versions,
            TypeOfStringResources.valueOf(typeOfStringResources.uppercase()),
            layers,
            generateUnitTest,
            GradleWrapper(Gradle.valueOf(gradle.uppercase())),
            develocity = develocityEnabled,
            projectName = projectName ?: buildString {
                append(typeOfProjectRequested.name.lowercase())
                append(shape.name.lowercase().replaceFirstChar { it.uppercase() })
                append(modules)
                append("modules")
            }
        ).write()
    }

    private fun parseYaml(rules: File): Versions {
        val mapper = ObjectMapper(YAMLFactory()).apply {
            registerModule(KotlinModule())
            configOverride(List::class.java).setterInfo = JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY)
        }
        return mapper.readValue(rules)
    }

    private fun getDevelocityEnabled(develocity: Boolean, develocityUrl: String?): Boolean {
        return if (develocity) {
            return true
        } else {
            develocityUrl != null
        }
    }

    private fun getVersions(fileVersions: File?, develocityUrl: String?, agp9: Boolean): Versions {
        val versions = if (fileVersions != null) {
            parseYaml(fileVersions)
        } else {
            // We only support the injection of AGP 9 version via CLI with the `agp9` option
            // if the version is provided by file this logic is not executed
            if (agp9) {
                val versions = Versions()
                versions.copy(android = Android(agp = versions.android.agp9))
            } else {
                Versions()
            }
        }
        return if (develocityUrl != null) {
            versions.copy(project = versions.project.copy(develocityUrl = develocityUrl))
        } else {
            versions
        }

    }
}

class GenerateYaml : CliktCommand(name = "generate-yaml-versions") {
    override fun run() {
        GenerateVersionsYaml().generate()
    }
}
