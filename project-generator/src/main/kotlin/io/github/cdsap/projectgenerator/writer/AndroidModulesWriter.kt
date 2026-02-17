package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.generator.buildfiles.BuildFilesGeneratorAndroid
import io.github.cdsap.projectgenerator.generator.classes.ClassGeneratorAndroid
import io.github.cdsap.projectgenerator.generator.classes.ClassGeneratorAndroidLegacy
import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryAndroid
import io.github.cdsap.projectgenerator.generator.planner.ModuleClassPlannerAndroid
import io.github.cdsap.projectgenerator.generator.planner.ModuleClassPlannerAndroidLegacy
import io.github.cdsap.projectgenerator.generator.resources.ResourceGenerator
import io.github.cdsap.projectgenerator.generator.test.TestGeneratorAndroid
import io.github.cdsap.projectgenerator.generator.test.TestGeneratorAndroidLegacy
import io.github.cdsap.projectgenerator.model.*

class AndroidModulesWriter(
    nodes: List<ProjectGraph>,
    languages: List<LanguageAttributes>,
    typeOfStringResources: TypeOfStringResources,
    generateUnitTest: Boolean,
    versions: Versions,
    di: DependencyInjection
) : ModulesWrite<ModuleClassDefinitionAndroid, GenerateDictionaryAndroid>(
    classGenerator = if (versions.android.roomDatabase) ClassGeneratorAndroid(di) else ClassGeneratorAndroidLegacy(di),
    classPlanner = if (versions.android.roomDatabase) ModuleClassPlannerAndroid() else ModuleClassPlannerAndroidLegacy(),
    testGenerator = if (versions.android.roomDatabase) TestGeneratorAndroid() else TestGeneratorAndroidLegacy(),
    resourceGeneratorA = ResourceGenerator(di, versions.android.roomDatabase),
    generateUnitTest = generateUnitTest,
    buildFilesGenerator = BuildFilesGeneratorAndroid(versions, di),
    resources = typeOfStringResources,
    nodes = nodes,
    languages = languages
)
