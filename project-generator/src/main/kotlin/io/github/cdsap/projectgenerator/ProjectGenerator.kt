package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import io.github.cdsap.projectgenerator.writer.GraphWriter

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
    private val dependencyPlugins: Boolean = false,
    private val develocity: Boolean = false,
) {

    fun write() {
        println("Calculating layer Distribution")
        val distributions = LayerDistribution(modules, layers).get(shape)
        println("Generating Project Dependency Graph")
        val nodes = ProjectGraphGenerator(
            if (shape == Shape.FLAT) 1 else layers,
            distributions,
            typeOfProjectRequested,
            classesPerModule
        ).generate()

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
            dependencyPlugins,
            develocity
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
}
