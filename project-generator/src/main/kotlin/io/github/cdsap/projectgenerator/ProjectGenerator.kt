package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import io.github.cdsap.projectgenerator.DefaultNames
import io.github.cdsap.projectgenerator.writer.GraphWriter
import io.github.cdsap.projectgenerator.writer.ProjectWriter

class ProjectGenerator(
    private val modules: Int,
    private val shape: Shape,
    private val language: Language = Language.KTS,
    private val typeOfProjectRequested: TypeProjectRequested = TypeProjectRequested.ANDROID,
    private val classesPerModule: ClassesPerModule = ClassesPerModule(type = ClassesPerModuleType.FIXED, classes = 5),
    private val versions: Versions = Versions(),
    private val typeOfStringResources: TypeOfStringResources = TypeOfStringResources.NORMAL,
    private val layers: Int,
    private val generateUnitTest: Boolean = false,
    private val gradle: GradleWrapper = GradleWrapper(Gradle.GRADLE_8_5),
    private val path: String = "projects_generated",
    private val develocity: Boolean = false,
    private val layerNames: List<String> = DefaultNames.layerNames,
    private val moduleNameParts: List<String> = DefaultNames.moduleNames,
) {

    fun write() {
        val nameProject = buildString {
            append(typeOfProjectRequested.name.lowercase())
            append(shape.name.lowercase().replaceFirstChar { it.uppercase() })
            append(modules)
            append("modules")
        }
        println("Creating project $nameProject in $path")
        println("Calculating layer Distribution")

        // Generate name mappings for layers and modules
        NameMappings.layerNames = (0..layers).associateWith { index ->
            if (index == layers) {
                "app"
            } else {
                layerNames.getOrNull(index) ?: "layer_$index"
            }
        }

        val distributions = LayerDistribution(modules, layers).get(shape)
        println("Generating Project Dependency Graph")
        val nodes = ProjectGraphGenerator(
            if (shape == Shape.FLAT) 1 else layers,
            distributions,
            typeOfProjectRequested,
            classesPerModule
        ).generate()

        NameMappings.moduleNames = nodes
            .sortedBy { it.id.substringAfterLast("_").toInt() }
            .mapIndexed { index, node ->
                if (node.layer == layers) {
                    node.id to "app"
                } else {
                    node.id to generateModuleName(index)
                }
            }.toMap()

        val projectLanguageAttributes =
            getProjectLanguageAttributes(language, "$path/${shape.name.lowercase()}_$modules")
        ProjectWriter(
            nodes,
            projectLanguageAttributes,
            versions,
            typeOfProjectRequested,
            typeOfStringResources,
            generateUnitTest,
            gradle,
            develocity,
            nameProject
        ).write()
        GraphWriter(nodes, "$path/${shape.name.lowercase()}_$modules").write()
        println("Project created in $path/${shape.name.lowercase()}_$modules")
    }

    private fun getProjectLanguageAttributes(language: Language, labelProject: String) = when (language) {
        Language.KTS -> listOf(LanguageAttributes("gradle.kts", "$labelProject/project_kts"))

        Language.GROOVY -> listOf(LanguageAttributes("gradle", "$labelProject/project_groovy"))

        Language.BOTH -> listOf(
            LanguageAttributes("gradle", "$labelProject/project_groovy"),
            LanguageAttributes("gradle.kts", "$labelProject/project_kts")
        )
    }

    private fun generateModuleName(index: Int): String {
        var remaining = index
        val base = moduleNameParts.size
        val parts = mutableListOf<String>()
        do {
            parts.add(moduleNameParts[remaining % base])
            remaining /= base
        } while (remaining > 0)
        return parts.joinToString("-")
    }
}
