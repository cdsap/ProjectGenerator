package io.github.cdsap.projectgenerator.generator.buildfiles

import io.github.cdsap.projectgenerator.writer.BuildFilesGenerator
import io.github.cdsap.projectgenerator.generator.toml.AndroidToml
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.NameMappings
import java.io.File

class BuildFilesGeneratorAndroid(
    private val versions: Versions,
    private val di: DependencyInjection
) : BuildFilesGenerator {
    override fun generateBuildFiles(
        node: ProjectGraph,
        lang: LanguageAttributes,
        generateUnitTests: Boolean
    ) {

        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        val buildFile = File("${lang.projectName}/$layerDir/$moduleDir/build.${lang.extension}")
        val buildContent = when (node.type) {
            TypeProject.ANDROID_APP -> createAndroidAppBuildFile(node, generateUnitTests)
            TypeProject.ANDROID_LIB -> createAndroidLibBuildFile(node, generateUnitTests)
            else -> ""
        }
        buildFile.writeText(buildContent)
    }

    private fun createAndroidAppBuildFile(node: ProjectGraph, generateUnitTests: Boolean): String {
        val implementations = mutableSetOf<String>()
        val testImplementations = mutableSetOf<String>()
        node.nodes.forEach { dependency ->
            if (dependency.layer != node.layer) {
                val dependencyPath = ":${NameMappings.layerName(dependency.layer)}:${NameMappings.moduleName(dependency.id)}"
                implementations.add("implementation(project(\"$dependencyPath\"))")
            }
        }

        val deps = AndroidToml().tomlImplementations(versions, di, versions.android.roomDatabase)
        return """
            |plugins {
            |    id("awesome.androidapp.plugin")
            |}
            |
            |dependencies {
${implementations.joinToString("\n").prependIndent("    ")}
${deps.prependIndent("    ")}
${testImplementations.joinToString("\n").prependIndent("    ")}
            |
            |}
        """.trimMargin()
    }

    private fun createAndroidLibBuildFile(node: ProjectGraph, generateUnitTests: Boolean): String {
        val implementations = mutableSetOf<String>()
        val testImplementations = mutableSetOf<String>()
        val deps = AndroidToml().tomlImplementations(versions, di, versions.android.roomDatabase)
        // Add direct dependencies first (only from different layers)
        node.nodes.forEach { dependency ->
            if (dependency.layer != node.layer) {
                val dependencyPath = ":${NameMappings.layerName(dependency.layer)}:${NameMappings.moduleName(dependency.id)}"
                implementations.add("implementation(project(\"$dependencyPath\"))")
                if (generateUnitTests) {
                    testImplementations.add("testImplementation(project(\"$dependencyPath\"))")
                }
            }
        }

        // Recursively collect all dependencies (only from different layers)
        fun collectAllDependencies(currentNode: ProjectGraph, visited: MutableSet<String> = mutableSetOf()) {
            if (currentNode.id in visited) return
            visited.add(currentNode.id)

            currentNode.nodes.forEach { dependency ->
                if (dependency.layer != node.layer) {
                val dependencyPath = ":${NameMappings.layerName(dependency.layer)}:${NameMappings.moduleName(dependency.id)}"
                    if (generateUnitTests) {
                        testImplementations.add("testImplementation(project(\"$dependencyPath\"))")
                    }
                    collectAllDependencies(dependency, visited)
                }
            }
        }

        // Start collecting from the current node's dependencies
        node.nodes.forEach { dependency ->
            collectAllDependencies(dependency)
        }

        return """
            |plugins {
            |    id("awesome.androidlib.plugin")
            |}
            |
            |dependencies {
${implementations.joinToString("\n").prependIndent("    ")}
${deps.prependIndent("    ")}
|${testImplementations.joinToString("\n").prependIndent("    ")}
            |
            |}
        """.trimMargin()
    }

}
