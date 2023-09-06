package io.github.cdsap.generator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import io.github.cdsap.generator.model.Language
import io.github.cdsap.generator.model.LanguageAttributes
import io.github.cdsap.generator.model.Shape
import io.github.cdsap.generator.writer.GraphWriter


fun main(args: Array<String>) {

    ProjectReportCli().main(args)

}

class ProjectReportCli : CliktCommand() {
    private val shape: String by option().choice("rhombus", "triangle", "flat", "rectangle", "middle_bottleneck")
        .required()
    private val language: String by option().choice("kts", "groovy", "both").required()
    private val modules by option().int().required().check("max number of projects 4000") { it <= 4000 }

    override fun run() {
        ProjectGenerator(
            modules,
            Shape.valueOf(shape.uppercase()),
            Language.valueOf(language.uppercase())
        ).write()
    }
}

class ProjectGenerator(
    private val numberOfModules: Int,
    private val shape: Shape,
    private val language: Language
) {

    fun write() {
        val numberOfLayer = 5

        println("Calculating layer Distribution")
        val distributions = LayerDistribution(numberOfModules, numberOfLayer).get(shape)
        println("Generating Project Dependency Graph")
        val nodes = if (shape == Shape.FLAT) {
            ProjectGraphGenerator(1, distributions).generate()
        } else {
            ProjectGraphGenerator(numberOfLayer, distributions).generate()
        }


        val projectLanguageAttributes =
            getProjectLanguageAttributes(language, "projects_generated/${shape.name.lowercase()}_$numberOfModules")
        ProjectWriter(nodes, projectLanguageAttributes, 5).write()
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

