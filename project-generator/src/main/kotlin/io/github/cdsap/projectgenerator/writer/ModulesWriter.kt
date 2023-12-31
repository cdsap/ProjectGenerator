package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.files.BuildGradleAndroidApp
import io.github.cdsap.projectgenerator.files.BuildGradleAndroidLib
import io.github.cdsap.projectgenerator.files.BuildGradleLib
import io.github.cdsap.projectgenerator.files.android.*
import io.github.cdsap.projectgenerator.model.*
import java.io.File
import java.util.*
import kotlin.random.Random

class ModulesWriter(
    private val nodes: List<ProjectGraph>,
    private val languages: List<LanguageAttributes>,
    private val typeOfStringResources: TypeOfStringResources,
    private val generateUnitTest: Boolean
) {

    fun write() {
        nodes.forEach {
            createModuleFoldersAndSource(it, languages)
            if (generateUnitTest) {
                createTestSource(it, languages)
            }
            createBuildGradleFile(it, languages)
        }
    }

    private fun createModuleFoldersAndSource(
        projectGraph: ProjectGraph,
        languages: List<LanguageAttributes>
    ) {

        val nodes = projectGraph.nodes


        for (i in 1..projectGraph.classes) {
            var referencesToAnotherModule = "\n"

            if (nodes.size > 1) {
                val numberOfImports = nodes.size / 2
                for (x in 0 until numberOfImports) {
                    val node = nodes.random()
                    val classToPick = Random.nextInt(1, node.classes)
                    referencesToAnotherModule += "        val dependencyClass$x = com.performance.${node.id.titleCase()}_$classToPick().${node.id.lowercase()}_$classToPick()\n"
                    referencesToAnotherModule += "        println(dependencyClass$x)\n"
                }
            } else if (nodes.size == 1) {
                for (x in 0 until Random.nextInt(1,4)) {
                    val node = nodes.random()
                    val classToPick = Random.nextInt(1, node.classes)
                    referencesToAnotherModule += "        val dependencyClass$x = com.performance.${node.id.titleCase()}_$classToPick().${node.id.lowercase()}_$classToPick()\n"
                    referencesToAnotherModule += "        println(dependencyClass$x)\n"
                }
            }


            val classContent = """package com.performance

class ${projectGraph.id.titleCase()}_$i {
    fun ${projectGraph.id.lowercase()}_$i() : String {
        val value = "${projectGraph.id.titleCase()}_$i"
        println("${projectGraph.id}")
        $referencesToAnotherModule
        return value
    }
}
"""

            languages.forEach {
                val directory =
                    File("${it.projectName}/layer_${projectGraph.layer}/${projectGraph.id}/src/main/kotlin/com/performance/")
                directory.mkdirs()
                File("${directory.path}/${projectGraph.id.titleCase()}_$i.kt").createNewFile()
                File("${directory.path}/${projectGraph.id.titleCase()}_$i.kt").writeText(classContent)
            }

        }
        if (projectGraph.type == TypeProject.ANDROID_LIB) {
            createAndroidLibResources(projectGraph, languages)
        }
        if (projectGraph.type == TypeProject.ANDROID_APP) {
            createAndroidAppResources(projectGraph, languages)
        }
    }

    fun createTestSource(
        projectGraph: ProjectGraph,
        languages: List<LanguageAttributes>
    ) {

        val nodes = projectGraph.nodes


        for (i in 1..projectGraph.classes) {
            var referencesToAnotherModule = "\n"

            if (nodes.size > 1) {
                val numberOfImports = nodes.size / 2
                for (x in 0 until numberOfImports) {
                    val node = nodes.random()
                    val classToPick = Random.nextInt(1, node.classes)
                    referencesToAnotherModule += "        val dependencyClass$x = com.performance.${node.id.titleCase()}_$classToPick().${node.id.lowercase()}_$classToPick()\n"
                    referencesToAnotherModule += "        println(dependencyClass$x)\n"
                }
            }


            val classContent = """package com.performance
import org.junit.Test
class ${projectGraph.id.titleCase()}_${i}_Test {
    @Test
    fun ${projectGraph.id.lowercase()}_$i() {
        val value = "${projectGraph.id.titleCase()}_$i"
        println("${projectGraph.id}")
        $referencesToAnotherModule
        assert(true)
    }
}
"""

            languages.forEach {
                val directory =
                    File("${it.projectName}/layer_${projectGraph.layer}/${projectGraph.id}/src/test/kotlin/com/performance/")
                directory.mkdirs()
                File("${directory.path}/${projectGraph.id.titleCase()}_$i.kt").createNewFile()
                File("${directory.path}/${projectGraph.id.titleCase()}_$i.kt").writeText(classContent)
            }

        }
    }

    private fun createAndroidAppResources(projectGraph: ProjectGraph, languages: List<LanguageAttributes>) {
        val manifest = AndroidManifestApp().get()
        val backupRules = BackupRules().get()
        val dataExtractionRules = DataExtractionRules().get()
        val drawableIcLauncherBackground = DrawableIcLauncherBackground().get()
        val drawableIcLauncherForeground = DrawableIcLauncherForeground().get()
        val valuesColors = ValuesColors().get()
        val valuesString = ValuesStrings().get()
        val valuesThemes = ValuesThemes().get()

        languages.forEach {
            val directory =
                File("${it.projectName}/layer_${projectGraph.layer}/${projectGraph.id}/src/main")
            val directoryDrawable =
                File("${directory.path}/res/drawable")
            val directoryValues = File("${directory.path}/res/values")
            val directoryXml = File("${directory.path}/res/xml")
            directoryDrawable.mkdirs()
            directoryValues.mkdirs()
            directoryXml.mkdirs()
            File("${directoryXml.path}/backup_rules.xml").createNewFile()
            File("${directoryXml.path}/backup_rules.xml").writeText(backupRules)

            File("${directoryXml.path}/data_extraction_rules.xml").createNewFile()
            File("${directoryXml.path}/data_extraction_rules.xml").writeText(dataExtractionRules)

            File("${directoryValues.path}/colors.xml").createNewFile()
            File("${directoryValues.path}/colors.xml").writeText(valuesColors)

            File("${directoryValues.path}/strings.xml").createNewFile()
            File("${directoryValues.path}/strings.xml").writeText(valuesString)

            File("${directoryValues.path}/themes.xml").createNewFile()
            File("${directoryValues.path}/themes.xml").writeText(valuesThemes)

            File("${directoryDrawable.path}/ic_launcher_background.xml").createNewFile()
            File("${directoryDrawable.path}/ic_launcher_background.xml").writeText(drawableIcLauncherBackground)

            File("${directoryDrawable.path}/ic_launcher_foreground.xml").createNewFile()
            File("${directoryDrawable.path}/ic_launcher_foreground.xml").writeText(drawableIcLauncherForeground)

            File("${directory.path}/AndroidManifest.xml").createNewFile()
            File("${directory.path}/AndroidManifest.xml").writeText(manifest)

        }
    }

    private fun createAndroidLibResources(projectGraph: ProjectGraph, languages: List<LanguageAttributes>) {
        val manifest = AndroidManifestLib().get()
        val valuesString =
            if (typeOfStringResources == TypeOfStringResources.NORMAL) ValuesStringsSmall().getLib(projectGraph.id)
            else ValuesStrings().getLib(projectGraph.id)
        val valuesColors = ValuesColors().getLib(projectGraph.id)
        val drawableIcLauncherBackground = DrawableIcLauncherBackground().get()
        val drawableIcLauncherForeground = DrawableIcLauncherForeground().get()

        languages.forEach {
            val directory =
                File("${it.projectName}/layer_${projectGraph.layer}/${projectGraph.id}/src/main")

            val directoryDrawable =
                File("${directory.path}/res/drawable")
            val directoryValues = File("${directory.path}/res/values")
            directoryDrawable.mkdirs()
            directoryValues.mkdirs()

            File("${directoryValues.path}/strings.xml").createNewFile()
            File("${directoryValues.path}/strings.xml").writeText(valuesString)

            File("${directoryValues.path}/colors.xml").createNewFile()
            File("${directoryValues.path}/colors.xml").writeText(valuesColors)

            File("${directoryDrawable.path}/${projectGraph.id}_ic_launcher_background.xml").createNewFile()
            File("${directoryDrawable.path}/${projectGraph.id}_ic_launcher_background.xml").writeText(
                drawableIcLauncherBackground
            )

            File("${directoryDrawable.path}/${projectGraph.id}_ic_launcher_foreground.xml").createNewFile()
            File("${directoryDrawable.path}/${projectGraph.id}_ic_launcher_foreground.xml").writeText(
                drawableIcLauncherForeground
            )

            File("${directory.path}/AndroidManifest.xml").createNewFile()
            File("${directory.path}/AndroidManifest.xml").writeText(manifest)

        }

    }

    private fun createBuildGradleFile(node: ProjectGraph, languages: List<LanguageAttributes>) {
        var dependencies = "    "
        node.nodes.forEach {
            val layer = it.id.split("_")
            dependencies += "\n    implementation(project(\":layer_${layer[1]}:${it.id}\"))"
        }
        val dependenciesBlock = """
dependencies {
${dependencies}
}
        """
        val plugins = getPluginToApply(node.type).trimIndent()
        val buildGradleContent = """
            |$plugins
            |$dependenciesBlock
        """.trimMargin()

        languages.forEach {
            File("${it.projectName}/layer_${node.layer}/${node.id}/build.${it.extension}").createNewFile()
            File("${it.projectName}/layer_${node.layer}/${node.id}/build.${it.extension}").writeText(buildGradleContent)
        }
    }

    private fun getPluginToApply(type: TypeProject): String {
        return when (type) {
            TypeProject.ANDROID_LIB -> BuildGradleAndroidLib().get()
            TypeProject.ANDROID_APP -> BuildGradleAndroidApp().get()
            TypeProject.LIB -> BuildGradleLib().get()
        }
    }
}

fun String.titleCase() = replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

