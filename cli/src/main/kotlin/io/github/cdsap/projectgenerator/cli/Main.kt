package io.github.cdsap.projectgenerator.cli

import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import io.github.cdsap.projectgenerator.ProjectGenerator
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import java.io.File

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
        "gradle_8_2",
        "gradle_8_5",
        "gradle_8_9",
        "gradle_8_13",
        "gradle_8_14_3"
    )
        .default("gradle_8_14_3")
    private val develocity by option().flag(default = false)
    private val versionsFile by option().file()


    override fun run() = ProjectGenerator(
        modules,
        Shape.valueOf(shape.uppercase()),
        Language.valueOf(language.uppercase()),
        TypeProjectRequested.valueOf(type.uppercase()),
        ClassesPerModule(ClassesPerModuleType.valueOf(classesModuleType.uppercase()), classesModule),
        versions = if (versionsFile != null) parseYaml(versionsFile!!) else Versions(),
        TypeOfStringResources.valueOf(typeOfStringResources.uppercase()),
        layers,
        generateUnitTest,
        GradleWrapper(Gradle.valueOf(gradle.uppercase())),
        develocity = develocity
    ).write()

    private fun parseYaml(rules: File): Versions {
        val mapper = ObjectMapper(YAMLFactory()).apply {
            registerModule(KotlinModule())
            configOverride(List::class.java).setterInfo = JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY)
        }
        return mapper.readValue(rules)
    }
}

class GenerateYaml : CliktCommand(name = "generate-yaml-versions") {
    override fun run() {
        GenerateVersionsYaml().generate()
    }
}
