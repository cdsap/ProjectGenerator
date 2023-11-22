package io.github.cdsap.generator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import io.github.cdsap.generator.model.*
import io.github.cdsap.generator.writer.GraphWriter

fun main(args: Array<String>) {
    ProjectReportCli().main(args)
}

class ProjectReportCli : CliktCommand() {
    private val shape: String by option().choice("rhombus", "triangle", "flat",
        "rectangle", "middle_bottleneck","inverse_triangle").required()
    private val language: String by option().choice("kts", "groovy", "both").default("kts")
    private val modules by option().int().required().check("max number of projects 4000") { it in (numberOfLayers + 1)..4000 }
    private val type by option().choice("android", "jvm").default("jvm")
    private val classes by option().int().default(5)
    private val agpVersion by option().default("8.1.4")
    private val kgpVersion by option().default("1.9.10")
    private val classesPerModule: String by option().choice("fixed", "random").default("fixed")
    private val typeOfStringResources: String by option().choice("large", "small").default("small")
    private val numberOfLayers by option().int().default(5)

    override fun run() {
        ProjectGenerator(
            modules,
            Shape.valueOf(shape.uppercase()),
            Language.valueOf(language.uppercase()),
            TypeProjectRequested.valueOf(type.uppercase()),
            ClassesPerModule(ClassesPerModuleType.valueOf(classesPerModule.uppercase()), classes),
            Versions(agp = agpVersion, kgp = kgpVersion),
            TypeOfStringResources.valueOf(typeOfStringResources.uppercase()),
            numberOfLayers
        ).write()
    }
}

class ProjectGenerator(
    private val numberOfModules: Int,
    private val shape: Shape,
    private val language: Language,
    private val typeOfProjectRequested: TypeProjectRequested,
    private val classesPerModule: ClassesPerModule,
    private val versions: Versions,
    private val typeOfStringResources: TypeOfStringResources,
    private val numberOfLayers: Int
) {

    fun write() {
        println("Calculating layer Distribution")
        val distributions = LayerDistribution(numberOfModules, numberOfLayers).get(shape)
        println("Generating Project Dependency Graph")
        val nodes = ProjectGraphGenerator(
            if (shape == Shape.FLAT) 1 else numberOfLayers,
            distributions,
            typeOfProjectRequested,
            classesPerModule
        ).generate()

        val projectLanguageAttributes =
            getProjectLanguageAttributes(language, "projects_generated/${shape.name.lowercase()}_$numberOfModules")
        ProjectWriter(
            nodes,
            projectLanguageAttributes,
            classesPerModule,
            versions,
            typeOfProjectRequested,
            typeOfStringResources
        ).write()
        GraphWriter(nodes, "projects_generated/${shape.name.lowercase()}_$numberOfModules").write()
        println("Project created in projects_generated/${shape.name.lowercase()}_$numberOfModules")
    }

    private fun getProjectLanguageAttributes(language: Language, labelProject: String) = when (language) {
        Language.KTS -> listOf(LanguageAttributes("gradle.kts", "$labelProject/project_kts"))

        Language.GROOVY -> listOf(LanguageAttributes("gradle", "$labelProject/project_groovy_$labelProject"))

        Language.BOTH -> listOf(
            LanguageAttributes("gradle", "$labelProject/project_groovy"),
            LanguageAttributes("gradle.kts", "$labelProject/project_kts")
        )
    }
}
