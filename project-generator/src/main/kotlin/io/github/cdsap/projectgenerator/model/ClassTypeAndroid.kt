package io.github.cdsap.projectgenerator.model

/**
 * Defines the types of classes we can generate
 */
enum class ClassTypeAndroid {
    VIEWMODEL,
    REPOSITORY,
    API,
    ENTITY,
    DAO,
    DATABASE,
    WORKER,
    ACTIVITY,
    FRAGMENT,
    SERVICE,
    STATE,
    SCREEN,
    MODEL,
    USECASE; // Semicolon might be needed if methods are present, adding for safety

    fun className () = this.name.lowercase().replaceFirstChar { it.uppercaseChar() }

    fun requiresDependency(): Boolean {
        return when (this) {
            VIEWMODEL -> true
            FRAGMENT -> true
            REPOSITORY -> true
            SERVICE -> true
            USECASE -> true
            DAO -> true
            SCREEN -> true
            else -> false
        }
    }

    fun dependencyType(): ClassTypeAndroid? {
        return when (this) {
            VIEWMODEL -> REPOSITORY
            FRAGMENT -> VIEWMODEL
            REPOSITORY -> API
            SERVICE -> REPOSITORY
            USECASE -> REPOSITORY
            DAO -> DATABASE
            SCREEN -> VIEWMODEL
            else -> null
        }
    }
}
