package io.github.cdsap.projectgenerator.generator.android

import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.NameMappings
import java.io.File

class ClassTheme {
    fun createThemeFile(node: ProjectGraph, lang: LanguageAttributes) {
        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        val themeDir =
            File("${lang.projectName}/$layerDir/$moduleDir/src/main/kotlin/com/awesomeapp/$moduleDir/ui/theme")
        themeDir.mkdirs()
        val themeFile = File(themeDir, "Theme.kt")
        val themeContent = """
            |package com.awesomeapp.$moduleDir.ui.theme
            |
            |import androidx.compose.material3.MaterialTheme
            |import androidx.compose.material3.lightColorScheme // Or darkColorScheme
            |import androidx.compose.runtime.Composable
            |
            |private val LightColorScheme = lightColorScheme(
            |    // Define basic colors or leave defaults
            |)
            |
            |@Composable
            |fun FeatureTheme(content: @Composable () -> Unit) {
            |    MaterialTheme(
            |        colorScheme = LightColorScheme,
            |        content = content
            |    )
            |}
        """.trimMargin()
        themeFile.writeText(themeContent)
    }
}
