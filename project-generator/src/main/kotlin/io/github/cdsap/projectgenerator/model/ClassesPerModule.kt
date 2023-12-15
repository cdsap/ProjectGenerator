package io.github.cdsap.projectgenerator.model

data class ClassesPerModule(
    val type: ClassesPerModuleType = ClassesPerModuleType.FIXED,
    val classes: Int
)

enum class ClassesPerModuleType {
    FIXED,
    RANDOM
}
