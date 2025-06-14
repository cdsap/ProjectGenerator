package io.github.cdsap.projectgenerator.generator.buildfiles

import io.github.cdsap.projectgenerator.writer.BuildFilesGenerator
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import java.io.File

class BuildFilesGeneratorJvm : BuildFilesGenerator {
    override fun generateBuildFiles(
        node: ProjectGraph,
        lang: LanguageAttributes
    ) {
        val buildFile = File("${lang.projectName}/layer_${node.layer}/${node.id}/build.${lang.extension}")
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
            |    ${node.nodes.joinToString("\n    ") { "implementation(project(\":layer_${it.layer}:${it.id}\"))" }}
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
            |    ${node.nodes.joinToString("\n    ") { "implementation(project(\":layer_${it.layer}:${it.id}\"))" }}
            |}
        """.trimMargin()
    }


}
