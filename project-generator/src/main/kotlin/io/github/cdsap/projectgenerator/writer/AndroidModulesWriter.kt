package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.generator.buildfiles.BuildFilesGeneratorAndroid
import io.github.cdsap.projectgenerator.generator.classes.ClassGeneratorAndroid
import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryAndroid
import io.github.cdsap.projectgenerator.generator.planner.ModuleClassPlannerAndroid
import io.github.cdsap.projectgenerator.generator.resources.ResourceGenerator
import io.github.cdsap.projectgenerator.generator.test.TestGeneratorAndroid
import io.github.cdsap.projectgenerator.model.*

class AndroidModulesWriter(
    nodes: List<ProjectGraph>,
    languages: List<LanguageAttributes>,
    typeOfStringResources: TypeOfStringResources,
    generateUnitTest: Boolean,
    versions: Versions,
    di: DependencyInjection
) : ModulesWrite<ModuleClassDefinitionAndroid, GenerateDictionaryAndroid>(
    classGenerator = ClassGeneratorAndroid(di),
    classPlanner = ModuleClassPlannerAndroid(),
    testGenerator = TestGeneratorAndroid(),
    resourceGeneratorA = ResourceGenerator(di),
    generateUnitTest = generateUnitTest,
    buildFilesGenerator = BuildFilesGeneratorAndroid(versions, di),
    resources = typeOfStringResources,
    nodes = nodes,
    languages = languages
)
