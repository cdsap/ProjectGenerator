package io.github.cdsap.projectgenerator.generator

import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import java.util.concurrent.CopyOnWriteArrayList

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
