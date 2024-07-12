package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import io.github.cdsap.projectgenerator.ProjectGenerator
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper

fun main(args: Array<String>) {
    ProjectReportCli().main(args)
}

class ProjectReportCli : CliktCommand() {
    private val shape: String by option().choice(
        "rhombus", "triangle", "flat",
        "rectangle", "middle_bottleneck", "inverse_triangle"
    ).required()
    private val language: String by option().choice("kts", "groovy", "both").default("kts")
    private val modules by option().int().required()
        .check("max number of projects 4000") { it in (layers + 1)..4000 }
    private val type by option().choice("android", "jvm").default("android")
    private val classesModule by option().int().default(5)
    private val agpVersion by option().default("8.5.0")
    private val kgpVersion by option().default("1.9.24")
    private val classesModuleType: String by option().choice("fixed", "random").default("fixed")
    private val typeOfStringResources: String by option().choice("large", "normal").default("normal")
    private val layers by option().int().default(5)
    private val generateUnitTest by option().flag(default = false)
    private val gradle: String by option().choice("gradle_8_2", "gradle_8_5").default("gradle_8_9")

    override fun run() = ProjectGenerator(
        modules,
        Shape.valueOf(shape.uppercase()),
        Language.valueOf(language.uppercase()),
        TypeProjectRequested.valueOf(type.uppercase()),
        ClassesPerModule(ClassesPerModuleType.valueOf(classesModuleType.uppercase()), classesModule),
        Versions(agp = agpVersion, kgp = kgpVersion),
        TypeOfStringResources.valueOf(typeOfStringResources.uppercase()),
        layers,
        generateUnitTest,
        GradleWrapper(Gradle.valueOf(gradle.uppercase()))
    ).write()
}
