package io.github.cdsap.projectgenerator.generator.resources

import io.github.cdsap.projectgenerator.generator.android.ActivityLayout
import io.github.cdsap.projectgenerator.generator.android.AndroidApplication
import io.github.cdsap.projectgenerator.generator.android.ClassTheme
import io.github.cdsap.projectgenerator.generator.android.FragmentLayout
import io.github.cdsap.projectgenerator.generator.android.Manifest
import io.github.cdsap.projectgenerator.generator.android.ValuesStrings
import io.github.cdsap.projectgenerator.writer.ResourceGeneratorA
import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryAndroid
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import io.github.cdsap.projectgenerator.model.TypeProject
import java.io.File

class ResourceGenerator() : ResourceGeneratorA<GenerateDictionaryAndroid> {

    override fun generate(
        node: ProjectGraph,
        lang: LanguageAttributes,
        typeOfStringResources: TypeOfStringResources,
        classesDictionary: MutableMap<String, MutableList<GenerateDictionaryAndroid>>
    ) {
        // Create resource directories
        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        File("${lang.projectName}/$layerDir/$moduleDir/src/main/res/layout").mkdirs()
        File("${lang.projectName}/$layerDir/$moduleDir/src/main/res/values").mkdirs()
        ClassTheme().createThemeFile(node, lang)

        when (node.type) {
            TypeProject.ANDROID_APP -> createAndroidAppResources(node, lang, classesDictionary, typeOfStringResources)
            TypeProject.ANDROID_LIB -> createAndroidLibResources(node, lang, classesDictionary, typeOfStringResources)
            else -> {} // No resources needed for other types
        }
    }

    private fun createAndroidAppResources(
        node: ProjectGraph,
        lang: LanguageAttributes,
        dictionary: MutableMap<String, MutableList<GenerateDictionaryAndroid>>,
        typeOfStringResources: TypeOfStringResources
    ) {
        val (layoutDir, valuesDir, manifestDir) = createResources(lang, node)
        Manifest().createManifest(manifestDir, node.layer, NameMappings.moduleName(node.id), TypeProject.ANDROID_APP, dictionary)
        AndroidApplication().createApplicationClass(node, lang)
        val moduleDir = NameMappings.moduleName(node.id)
        createLayoutFiles(layoutDir, moduleDir)
        createValueFiles(valuesDir, moduleDir, typeOfStringResources)
    }

    private fun createAndroidLibResources(
        node: ProjectGraph, lang: LanguageAttributes,
        dictionary: MutableMap<String, MutableList<GenerateDictionaryAndroid>>,
        typeOfStringResources: TypeOfStringResources
    ) {
        val (layoutDir, valuesDir, manifestDir) = createResources(lang, node)
        Manifest().createManifest(manifestDir, node.layer, NameMappings.moduleName(node.id), TypeProject.ANDROID_LIB, dictionary)
        val moduleDirLib = NameMappings.moduleName(node.id)
        createLayoutFiles(layoutDir, moduleDirLib)
        createValueFiles(valuesDir, moduleDirLib, typeOfStringResources)
    }

    private fun createResources(
        lang: LanguageAttributes,
        node: ProjectGraph
    ): Triple<File, File, File> {
        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        val layoutDir = File("${lang.projectName}/$layerDir/$moduleDir/src/main/res/layout")
        val valuesDir = File("${lang.projectName}/$layerDir/$moduleDir/src/main/res/values")
        val manifestDir = File("${lang.projectName}/$layerDir/$moduleDir/src/main/")
        return Triple(layoutDir, valuesDir, manifestDir)
    }

    private fun createLayoutFiles(layoutDir: File, moduleId: String) {
        layoutDir.mkdirs()
        val activityLayout = ActivityLayout().createActivityLayout(moduleId)
        File(layoutDir, "activity_feature_${NameMappings.modulePackageName(moduleId).lowercase()}.xml").writeText(activityLayout)
        val fragmentLayout = FragmentLayout().createFragmentLayout(moduleId)
        File(layoutDir, "fragment_feature_${NameMappings.modulePackageName(moduleId).lowercase()}.xml").writeText(fragmentLayout)
    }

    private fun createValueFiles(valuesDir: File, moduleId: String, typeOfStringResources: TypeOfStringResources) {
        valuesDir.mkdirs()
        val strings = ValuesStrings().createStrings(moduleId.split("_").last(), typeOfStringResources)
        File(valuesDir, "strings.xml").writeText(strings)
    }

}
