package io.github.cdsap.generator.writer

import io.github.cdsap.generator.files.GradleProperties
import io.github.cdsap.generator.model.Language
import io.github.cdsap.generator.model.LanguageAttributes
import io.github.cdsap.generator.model.ProjectGraph
import java.io.File
import java.util.*

class ModulesWriter(
    val nodes: List<ProjectGraph>,
    val languages: List<LanguageAttributes>,
    val numberOfClassPerModule: Int
) {

    fun write() {
        nodes.forEach {
            createModuleFoldersAndSource(it, languages, numberOfClassPerModule)
            createBuildGradleFile(it, languages)
        }
    }

    fun createModuleFoldersAndSource(
        projectGraph: ProjectGraph,
        languages: List<LanguageAttributes>,
        numberOfClassPerModule: Int
    ) {

        val nodes = projectGraph.nodes

        for (i in 1..numberOfClassPerModule) {
            var referencesToAnotherModule = ""

            if (nodes.size > 1) {
                val numberOfImports = nodes.size / 2
                for (x in 0 until numberOfImports) {
                    val node = nodes.random()
                    referencesToAnotherModule += " val dependencyClass$x = com.performance.${node.titleCase()}_1()\n"
                }
            }

            val classContent = """
        package com.performance

        class ${projectGraph.id.titleCase()}_$i {
           fun alo() {
             println("${projectGraph.id}")
             $referencesToAnotherModule
             }
        }
    """.trimIndent()

            languages.forEach {
                val directory =
                    File("${it.projectName}/layer_${projectGraph.layer}/${projectGraph.id}/src/main/kotlin/com/performance/")
                directory.mkdirs()
                File("${directory.path}/${projectGraph.id.titleCase()}_$i.kt").createNewFile()
                File("${directory.path}/${projectGraph.id.titleCase()}_$i.kt").writeText(classContent)
            }
        }
    }

    private fun createBuildGradleFile(node: ProjectGraph, languages: List<LanguageAttributes>) {
        var dependencies = ""
        node.nodes.forEach {
            val layer = it.split("_")
            dependencies += "\n     implementation(project(\":layer_${layer[1]}:$it\"))"
        }
        val buildGradleContent = """
            plugins {
               id ("java-library")
               id ("maven-publish")
               id ("jacoco")
               id ("org.sonarqube") version "4.3.0.3225"
               id("awesome.kotlin.plugin")
            }
               dependencies {
                  $dependencies
               }

        """.trimIndent()
        languages.forEach {
            File("${it.projectName}/layer_${node.layer}/${node.id}/build.${it.extension}").createNewFile()
            File("${it.projectName}/layer_${node.layer}/${node.id}/build.${it.extension}").writeText(buildGradleContent)
        }
    }
}

fun String.titleCase() = replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

