package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import io.github.cdsap.projectgenerator.writer.GraphWriter
import io.github.cdsap.projectgenerator.writer.ProjectWriter

class ProjectGenerator(
    private val modules: Int,
    private val shape: Shape,
    private val language: Language = Language.KTS,
    private val typeOfProjectRequested: TypeProjectRequested = TypeProjectRequested.ANDROID,
    private val classesPerModule: ClassesPerModule = ClassesPerModule(type = ClassesPerModuleType.FIXED, classes = 10),
    private val versions: Versions = Versions(),
    private val typeOfStringResources: TypeOfStringResources = TypeOfStringResources.NORMAL,
    private val layers: Int,
    private val generateUnitTest: Boolean = false,
    private val gradle: GradleWrapper = GradleWrapper(Gradle.latest()),
    private val projectRootPath: String = "projects_generated/generated_project/project_kts",
    private val develocity: Boolean = false,
    private val layerNames: List<String> = DefaultNames.layerNames,
    private val moduleNameParts: List<String> = DefaultNames.moduleNames,
    private val projectName: String
) {

    fun write() {

        println("Creating project $projectName in $projectRootPath")
        println("Calculating layer Distribution")

        val distributions = LayerDistribution(modules, layers).get(shape)
        println("Generating Project Dependency Graph")
        val nodes = ProjectGraphGenerator(
            if (shape == Shape.FLAT) 1 else layers,
            distributions,
            typeOfProjectRequested,
            classesPerModule
        ).generate()

        NameMappings.configure(
            ProjectNameMappingFactory.create(
                layers = layers,
                nodes = nodes,
                layerNames = layerNames,
                moduleNameParts = moduleNameParts
            )
        )

        val projectLanguageAttributes = getProjectLanguageAttributes()
        ProjectWriter(
            nodes,
            projectLanguageAttributes,
            versions,
            typeOfProjectRequested,
            typeOfStringResources,
            generateUnitTest,
            gradle,
            develocity,
            projectName
        ).write()
        projectLanguageAttributes.forEach { attributes ->
            GraphWriter(nodes, attributes.projectName).write()
        }
        println("Project created in ${projectLanguageAttributes.first().projectName}")
    }

    private fun getProjectLanguageAttributes(): List<LanguageAttributes> {
        return when (language) {
            Language.KTS -> listOf(
                LanguageAttributes("gradle.kts", projectRootPath)
            )

            Language.GROOVY -> listOf(
                LanguageAttributes("gradle", projectRootPath)
            )

            Language.BOTH -> listOf(
                LanguageAttributes("gradle", "$projectRootPath/project_groovy"),
                LanguageAttributes("gradle.kts", "$projectRootPath/project_kts")
            )
        }
    }
}
