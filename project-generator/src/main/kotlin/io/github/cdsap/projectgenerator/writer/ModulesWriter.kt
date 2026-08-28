package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.generator.GeneratedModuleLayout
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

abstract class ModulesWrite<MODULE_DEF, DICT>(
    private val classGenerator: ClassGenerator<MODULE_DEF, DICT>,
    private val classPlanner: ModuleClassPlanner<MODULE_DEF>,
    private val testGenerator: TestGenerator<MODULE_DEF, DICT>,
    private val resourceGeneratorA: ResourceGeneratorA<DICT>? = null,
    private val generateUnitTest: Boolean,
    private val buildFilesGenerator: BuildFilesGenerator,
    private val resources: TypeOfStringResources? = null,
    private val nodes: List<ProjectGraph>,
    private val languages: List<LanguageAttributes>,
    private val sourceSetLayout: ModuleSourceSetLayout = JvmModuleSourceSetLayout
) {
    suspend fun write() = coroutineScope {
        val classesDictionary = ConcurrentHashMap<String, CopyOnWriteArrayList<DICT>>()

        val groupedByLayer = nodes.groupBy { it.layer }.toSortedMap()

        // Process setup jobs layer by layer
        for ((_, layerNodes) in groupedByLayer) {
            val setupJobs = layerNodes.map { module ->
                async(Dispatchers.Default) {
                    val moduleDefinition = classPlanner.planModuleClasses(module)
                    classGenerator.obtainClassesGenerated(moduleDefinition, classesDictionary)

                    languages.forEach { lang ->
                        createModuleStructure(module, lang)
                        classGenerator.generate(moduleDefinition, lang.projectName, classesDictionary)
                        buildFilesGenerator.generateBuildFiles(module, lang, generateUnitTest)
                        resourceGeneratorA?.generate(module, lang, resources!!, classesDictionary)
                    }
                }
            }
            setupJobs.awaitAll()
        }

        // Process test generation layer by layer if needed
        if (generateUnitTest) {
            for ((_, layerNodes) in groupedByLayer) {
                val testJobs = layerNodes.map { module ->
                    async(Dispatchers.Default) {
                        val plan = classPlanner.planModuleClasses(module)
                        languages.forEach { lang ->
                            testGenerator.generate(plan, lang.projectName, classesDictionary)
                        }
                    }
                }
                testJobs.awaitAll()
            }
        }
    }

    private fun createModuleStructure(node: ProjectGraph, lang: LanguageAttributes) {
        val layout = GeneratedModuleLayout.of(
            projectName = lang.projectName,
            node = node,
            mainKotlinSourceDir = sourceSetLayout.mainKotlinDir(node),
            testKotlinSourceDir = sourceSetLayout.testKotlinDir(node)
        )
        layout.mainKotlinPackageDir().mkdirs()

        if (generateUnitTest) {
            layout.testKotlinPackageDir().mkdirs()
        }
    }
}

interface ModuleClassPlanner<MODULE_DEF> {
    fun planModuleClasses(node: ProjectGraph): MODULE_DEF
}

interface ClassGenerator<MODULE_DEF, DICT> {
    fun generate(
        moduleDefinition: MODULE_DEF,
        projectName: String,
        a: MutableMap<String, CopyOnWriteArrayList<DICT>>
    )

    fun obtainClassesGenerated(
        moduleDefinition: MODULE_DEF,
        classesDictionary: MutableMap<String, CopyOnWriteArrayList<DICT>>
    ): MutableMap<String, CopyOnWriteArrayList<DICT>>
}

interface TestGenerator<MODULE_DEF, DICT> {
    fun generate(
        moduleDefinition: MODULE_DEF,
        projectName: String,
        classesDictionary: MutableMap<String, CopyOnWriteArrayList<DICT>>
    )
}

interface BuildFilesGenerator {
    fun generateBuildFiles(node: ProjectGraph, lang: LanguageAttributes, generateUnitTests: Boolean)
}

interface ResourceGeneratorA<DICT> {
    fun generate(
        node: ProjectGraph, lang: LanguageAttributes,
        typeOfStringResources: TypeOfStringResources,
        classesDictionary: MutableMap<String, CopyOnWriteArrayList<DICT>>
    )
}
