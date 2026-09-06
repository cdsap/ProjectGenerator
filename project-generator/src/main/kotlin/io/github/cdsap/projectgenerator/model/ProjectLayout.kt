package io.github.cdsap.projectgenerator.model

object ProjectLayout {
    fun defaultRootPath(outputDir: String?, language: Language, projectName: String): String {
        return if (outputDir != null) {
            outputDir
        } else {
            when (language) {
                Language.KTS -> "projects_generated/$projectName/project_kts"
                Language.GROOVY -> "projects_generated/$projectName/project_groovy"
                Language.BOTH -> "projects_generated/$projectName"
            }
        }
    }

    fun languageAttributes(rootPath: String, language: Language): List<LanguageAttributes> {
        return when (language) {
            Language.KTS -> listOf(
                LanguageAttributes("gradle.kts", rootPath)
            )

            Language.GROOVY -> listOf(
                LanguageAttributes("gradle", rootPath)
            )

            Language.BOTH -> listOf(
                LanguageAttributes("gradle", "$rootPath/project_groovy"),
                LanguageAttributes("gradle.kts", "$rootPath/project_kts")
            )
        }
    }
}
