package io.github.cdsap.projectgenerator.cli

import io.github.cdsap.projectgenerator.model.Language

object ProjectOutputPathResolver {
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
}
