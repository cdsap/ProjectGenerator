package io.github.cdsap.projectgenerator.model

object ProjectLayout {
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
