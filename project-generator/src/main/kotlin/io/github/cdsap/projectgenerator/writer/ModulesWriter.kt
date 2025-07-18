package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import java.io.File

abstract class ModulesWrite<MODULE_DEF, DICT>(
    private val classGenerator: ClassGenerator<MODULE_DEF, DICT>,
    private val classPlanner: ModuleClassPlanner<MODULE_DEF>,
    private val testGenerator: TestGenerator<MODULE_DEF, DICT>,
    private val resourceGeneratorA: ResourceGeneratorA<DICT>? = null,
    private val generateUnitTest: Boolean,
    private val buildFilesGenerator: BuildFilesGenerator,
    private val resources: TypeOfStringResources? = null,
    private val nodes: List<ProjectGraph>,
    private val languages: List<LanguageAttributes>
) {
    fun write() {
        val classesDictionary = mutableMapOf<String, MutableList<DICT>>()

        nodes.forEach { module ->
            val moduleDefinition = classPlanner.planModuleClasses(module)

            classGenerator.obtainClassesGenerated(moduleDefinition, classesDictionary)
            val plan = classPlanner.planModuleClasses(module)
            languages.forEach { lang ->
                createModuleStructure(module, lang)
                classGenerator.generate(plan, lang.projectName, classesDictionary)
                buildFilesGenerator.generateBuildFiles(module, lang, generateUnitTest)
                resourceGeneratorA?.generate(
                    module,
                    lang,
                    resources!!,
                    classesDictionary
                )

            }
        }

        if (generateUnitTest) {
            nodes.forEach { module ->
                val plan = classPlanner.planModuleClasses(module)
                languages.forEach { lang ->
                    testGenerator.generate(plan, lang.projectName, classesDictionary)
                }
            }
        }
    }

    private fun createModuleStructure(node: ProjectGraph, lang: LanguageAttributes) {
        // Create main source directory
        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        File("${lang.projectName}/$layerDir/$moduleDir/src/main/kotlin/com/awesomeapp/$moduleDir/").mkdirs()

        // Create test directory if needed
        if (generateUnitTest) {
            File("${lang.projectName}/$layerDir/$moduleDir/src/test/kotlin/com/awesomeapp/$moduleDir/").mkdirs()
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
        a: MutableMap<String, MutableList<DICT>>
    )

    fun obtainClassesGenerated(
        moduleDefinition: MODULE_DEF,
        classesDictionary: MutableMap<String, MutableList<DICT>>
    ): MutableMap<String, MutableList<DICT>>
}

interface TestGenerator<MODULE_DEF, DICT> {
    fun generate(
        moduleDefinition: MODULE_DEF,
        projectName: String,
        classesDictionary: MutableMap<String, MutableList<DICT>>
    )
}

interface BuildFilesGenerator {
    fun generateBuildFiles(node: ProjectGraph, lang: LanguageAttributes, generateUnitTests: Boolean)
}

interface ResourceGeneratorA<DICT> {
    fun generate(
        node: ProjectGraph, lang: LanguageAttributes,
        typeOfStringResources: TypeOfStringResources,
        classesDictionary: MutableMap<String, MutableList<DICT>>
    )
}
