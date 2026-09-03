package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.generator.BuildFilesGenerator
import io.github.cdsap.projectgenerator.generator.ClassGenerator
import io.github.cdsap.projectgenerator.generator.GeneratedModuleLayout
import io.github.cdsap.projectgenerator.generator.ModuleClassPlanner
import io.github.cdsap.projectgenerator.generator.ResourceGeneratorA
import io.github.cdsap.projectgenerator.generator.TestGenerator
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
