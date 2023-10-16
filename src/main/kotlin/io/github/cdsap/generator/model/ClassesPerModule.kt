package io.github.cdsap.generator.model

data class ClassesPerModule(
    val type: ClassesPerModuleType,
    val classes: Int
)

enum class ClassesPerModuleType {
    FIXED,
    RANDOM
}
