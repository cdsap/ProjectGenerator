package io.github.cdsap.projectgenerator.generator.resources

import io.github.cdsap.projectgenerator.generator.android.ActivityLayout
import io.github.cdsap.projectgenerator.generator.android.AndroidApplication
import io.github.cdsap.projectgenerator.generator.android.ClassTheme
import io.github.cdsap.projectgenerator.generator.android.FragmentLayout
import io.github.cdsap.projectgenerator.generator.android.Manifest
import io.github.cdsap.projectgenerator.generator.android.ValuesStrings
import io.github.cdsap.projectgenerator.writer.ResourceGeneratorA
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
        File("${lang.projectName}/layer_${node.layer}/${node.id}/src/main/res/layout").mkdirs()
        File("${lang.projectName}/layer_${node.layer}/${node.id}/src/main/res/values").mkdirs()
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
        Manifest().createManifest(manifestDir, node.layer, node.id, TypeProject.ANDROID_APP, dictionary)
        AndroidApplication().createApplicationClass(node, lang)
        createLayoutFiles(layoutDir, node.id)
        createValueFiles(valuesDir, node.id, typeOfStringResources)
    }

    private fun createAndroidLibResources(
        node: ProjectGraph, lang: LanguageAttributes,
        dictionary: MutableMap<String, MutableList<GenerateDictionaryAndroid>>,
        typeOfStringResources: TypeOfStringResources
    ) {
        val (layoutDir, valuesDir, manifestDir) = createResources(lang, node)
        Manifest().createManifest(manifestDir, node.layer, node.id, TypeProject.ANDROID_LIB, dictionary)
        createLayoutFiles(layoutDir, node.id)
        createValueFiles(valuesDir, node.id, typeOfStringResources)
    }

    private fun createResources(
        lang: LanguageAttributes,
        node: ProjectGraph
    ): Triple<File, File, File> {
        val layoutDir = File("${lang.projectName}/layer_${node.layer}/${node.id}/src/main/res/layout")
        val valuesDir = File("${lang.projectName}/layer_${node.layer}/${node.id}/src/main/res/values")
        val manifestDir = File("${lang.projectName}/layer_${node.layer}/${node.id}/src/main/")
        return Triple(layoutDir, valuesDir, manifestDir)
    }

    private fun createLayoutFiles(layoutDir: File, moduleId: String) {
        layoutDir.mkdirs()
        val activityLayout = ActivityLayout().createActivityLayout(moduleId)
        File(layoutDir, "activity_feature_${moduleId.lowercase()}.xml").writeText(activityLayout)
        val fragmentLayout = FragmentLayout().createFragmentLayout(moduleId)
        File(layoutDir, "fragment_feature_${moduleId.lowercase()}.xml").writeText(fragmentLayout)
    }

    private fun createValueFiles(valuesDir: File, moduleId: String, typeOfStringResources: TypeOfStringResources) {
        valuesDir.mkdirs()
        val strings = ValuesStrings().createStrings(moduleId.split("_").last(), typeOfStringResources)
        File(valuesDir, "strings.xml").writeText(strings)
    }

}
