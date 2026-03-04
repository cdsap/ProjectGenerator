package io.github.cdsap.projectgenerator.model

data class ClassesPerModule(
    val type: ClassesPerModuleType = ClassesPerModuleType.FIXED,
    val classes: Int
) {
    init {
        require(classes >= MIN_CLASSES_PER_MODULE) {
            "classes per module must be >= $MIN_CLASSES_PER_MODULE"
        }
    }

    companion object {
        const val MIN_CLASSES_PER_MODULE = 10
    }
}

enum class ClassesPerModuleType {
    FIXED,
    RANDOM
}
