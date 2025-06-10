package io.github.cdsap.projectgenerator.model
/**
 * Defines what classes a module should contain and their dependencies
 */
data class ModuleClassDefinition<TYPE, CLASS_DEF>(
    val moduleId: String,
    val layer: Int,
    val moduleNumber: Int,
    val classes: List<CLASS_DEF>,
    val dependencies: List<String> = emptyList()
)

/**
 * Defines a specific class in a module
 */
data class ClassDefinition<TYPE, DEP>(
    val type: TYPE,
    val index: Int,
    val dependencies: MutableList<DEP> = mutableListOf()
)

/**
 * Defines a dependency on another class
 */
data class ClassDependency<TYPE>(
    val type: TYPE,
    val sourceModuleId: String
)

// Android Types
typealias ModuleClassDefinitionAndroid = ModuleClassDefinition<ClassTypeAndroid, ClassDefinitionAndroid>
typealias ClassDefinitionAndroid = ClassDefinition<ClassTypeAndroid, ClassDependencyAndroid>
typealias ClassDependencyAndroid = ClassDependency<ClassTypeAndroid>

// JVM Types
typealias ModuleClassDefinitionJvm = ModuleClassDefinition<ClassTypeJvm, ClassDefinitionJvm>
typealias ClassDefinitionJvm = ClassDefinition<ClassTypeJvm, ClassDependencyJvm>
typealias ClassDependencyJvm = ClassDependency<ClassTypeJvm>
