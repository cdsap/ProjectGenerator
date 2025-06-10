package io.github.cdsap.projectgenerator.model

import io.github.cdsap.projectgenerator.model.ClassDependency

/**
 * Defines the types of classes we can generate
 */
enum class ClassTypeJvm {
    REPOSITORY,
    API,
    STATE,
    MODEL,
    USECASE; // Semicolon might be needed if methods are present, adding for safety

    fun className () = this.name.lowercase().replaceFirstChar { it.uppercaseChar() }

    fun requiresDependency(): Boolean {
        return when (this) {
            REPOSITORY -> true
            USECASE -> true
            else -> false
        }
    }

    fun dependencyType(): ClassTypeJvm? {
        return when (this) {
            REPOSITORY -> API
            USECASE -> REPOSITORY
            else -> null
        }
    }
}
