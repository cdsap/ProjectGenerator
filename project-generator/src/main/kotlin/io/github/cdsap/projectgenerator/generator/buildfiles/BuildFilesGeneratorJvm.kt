package io.github.cdsap.projectgenerator.generator.buildfiles

import io.github.cdsap.projectgenerator.writer.BuildFilesGenerator
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import io.github.cdsap.projectgenerator.NameMappings
import java.io.File

class BuildFilesGeneratorJvm : BuildFilesGenerator {
    override fun generateBuildFiles(
        node: ProjectGraph,
        lang: LanguageAttributes,
        generateUnitTests: Boolean
    ) {
        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        val buildFile = File("${lang.projectName}/$layerDir/$moduleDir/build.${lang.extension}")
        val buildContent = when (node.type) {
            TypeProject.LIB -> createLibBuildFile(node)
            TypeProject.APPLICATION -> createApplicationBuildFile(node)
            else ->  ""
        }
        buildFile.writeText(buildContent)
    }


    private fun createApplicationBuildFile(node: ProjectGraph): String {
        return """
            |plugins {
            |    id("awesome.kotlin.plugin")
            |}
            |
            |dependencies {
            |    ${node.nodes.joinToString("\n    ") { "implementation(project(\":${NameMappings.layerName(it.layer)}:${NameMappings.moduleName(it.id)}\"))" }}
            |}
        """.trimMargin()
    }

    private fun createLibBuildFile(node: ProjectGraph): String {
        return """
            |plugins {
            |    id("awesome.kotlin.plugin")
            |}
            |
            |dependencies {
            |    ${node.nodes.joinToString("\n    ") { "implementation(project(\":${NameMappings.layerName(it.layer)}:${NameMappings.moduleName(it.id)}\"))" }}
            |}
        """.trimMargin()
    }


}
