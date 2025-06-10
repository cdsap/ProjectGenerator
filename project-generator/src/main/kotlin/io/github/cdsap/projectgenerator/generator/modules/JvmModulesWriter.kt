package io.github.cdsap.projectgenerator.generator.modules

import io.github.cdsap.projectgenerator.generator.buildfiles.BuildFilesGeneratorJvm
import io.github.cdsap.projectgenerator.generator.planner.ModuleClassPlannerJvm
import io.github.cdsap.projectgenerator.generator.ModulesWrite
import io.github.cdsap.projectgenerator.generator.classes.ClassGeneratorJvm
import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryJvm
import io.github.cdsap.projectgenerator.generator.test.TestGeneratorJvm
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ModuleClassDefinitionJvm
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.Versions


class JvmModulesWriter(
    nodes: List<ProjectGraph>,
    languages: List<LanguageAttributes>,
    generateUnitTest: Boolean,
    versions: Versions
) : ModulesWrite<ModuleClassDefinitionJvm, GenerateDictionaryJvm>(
    classGenerator = ClassGeneratorJvm(),
    classPlanner = ModuleClassPlannerJvm(),
    testGenerator = TestGeneratorJvm(),
    generateUnitTest = generateUnitTest,
    buildFilesGenerator = BuildFilesGeneratorJvm(),
    nodes = nodes,
    languages = languages
)
