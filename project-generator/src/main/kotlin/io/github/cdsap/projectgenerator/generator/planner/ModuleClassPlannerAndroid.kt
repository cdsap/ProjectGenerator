package io.github.cdsap.projectgenerator.generator.planner

import io.github.cdsap.projectgenerator.writer.ModuleClassPlanner
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.NameMappings

/**
 * Plans what classes each module should have and their dependencies
 */
class ModuleClassPlannerAndroid : ModuleClassPlanner<ModuleClassDefinitionAndroid> {
    override fun planModuleClasses(projectGraph: ProjectGraph): ModuleClassDefinitionAndroid {
        val moduleId = NameMappings.moduleName(projectGraph.id)
        val layer = projectGraph.layer
        val moduleNumber = projectGraph.id.split("_").last().toInt()
        val classes = mutableListOf<ClassDefinitionAndroid>()
        var currentIndex = 1

        // First, add all core classes in the correct order
        val coreClasses = mutableListOf<ClassDefinitionAndroid>()

        // Every module gets a ViewModel
        coreClasses.add(
            ClassDefinitionAndroid(
                type = ClassTypeAndroid.VIEWMODEL,
                index = currentIndex++,
                dependencies = findDependenciesForClass(ClassTypeAndroid.VIEWMODEL, projectGraph).toMutableList()
            )
        )

        // If we have more than 1 class, add Activity
        if (projectGraph.classes > 1) {
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.ACTIVITY,
                    index = currentIndex++,
                    dependencies = mutableListOf() // Activity doesn't need Fragment dependency yet
                )
            )
        }

        // If we have more than 2 classes, add Compose Activity
        if (projectGraph.classes > 2) {
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.ACTIVITY,
                    index = currentIndex++,
                    dependencies = mutableListOf() // Compose Activity doesn't need Fragment dependency yet
                )
            )
        }

        // If we have more than 3 classes, add Fragment
        if (projectGraph.classes > 3) {
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.FRAGMENT,
                    index = currentIndex++,
                    dependencies = findDependenciesForClass(ClassTypeAndroid.FRAGMENT, projectGraph).toMutableList()
                )
            )
        }

        // If we have more than 4 classes and it's a multiple of 4, add Repository and API
        if (projectGraph.classes > 4 && moduleNumber % 4 == 0) {
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.REPOSITORY,
                    index = currentIndex++,
                    dependencies = findDependenciesForClass(ClassTypeAndroid.REPOSITORY, projectGraph).toMutableList()
                )
            )
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.API,
                    index = currentIndex++
                )
            )
        }

        // If we have more than 6 classes and it's a multiple of 5, add Service and Worker
        if (projectGraph.classes > 6 && moduleNumber % 5 == 0) {
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.SERVICE,
                    index = currentIndex++,
                    dependencies = findDependenciesForClass(ClassTypeAndroid.SERVICE, projectGraph).toMutableList()
                )
            )
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.WORKER,
                    index = currentIndex++
                )
            )
        }

        // If we have more than 8 classes and it's a multiple of 3, add UseCase
        if (projectGraph.classes > 8 && moduleNumber % 3 == 0) {
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.USECASE,
                    index = currentIndex++,
                    dependencies = findDependenciesForClass(ClassTypeAndroid.USECASE, projectGraph).toMutableList()
                )
            )
        }

        // If we have more than 9 classes, add State
        if (projectGraph.classes > 9) {
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.STATE,
                    index = currentIndex++
                )
            )
        }

        // If we have more than 10 classes, add Model
        if (projectGraph.classes > 10) {
            coreClasses.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.MODEL,
                    index = currentIndex++
                )
            )
        }

        // Add core classes to the final list
        classes.addAll(coreClasses)

        // Add additional classes to reach the requested number
        val remainingClasses = projectGraph.classes - classes.size
        if (remainingClasses > 0) {
            // Create additional classes with simpler types that don't require dependencies
            for (i in 1..remainingClasses) {
                val classType = when (i % 3) {
                    0 -> ClassTypeAndroid.STATE
                    1 -> ClassTypeAndroid.MODEL
                    else -> ClassTypeAndroid.ACTIVITY
                }

                classes.add(
                    ClassDefinitionAndroid(
                        type = classType,
                        index = currentIndex++,
                        dependencies = mutableListOf()
                    )
                )
            }
        }

        // Now update Activity dependencies to reference the correct Fragment
        classes.forEach { classDef ->
            if (classDef.type == ClassTypeAndroid.ACTIVITY) {
                val fragment = classes.find { it.type == ClassTypeAndroid.FRAGMENT }
                if (fragment != null) {
                    classDef.dependencies.add(ClassDependencyAndroid(ClassTypeAndroid.FRAGMENT, moduleId))
                }
            }
        }

        // Extract the module IDs from node dependencies for proper Hilt module dependencies
        val dependencies = projectGraph.nodes.map { NameMappings.moduleName(it.id) }

        return ModuleClassDefinitionAndroid(
            moduleId = moduleId,
            layer = layer,
            moduleNumber = moduleNumber,
            classes = classes,
            dependencies = dependencies
        )
    }

    private fun findDependenciesForClass(
        classType: ClassTypeAndroid,
        currentModule: ProjectGraph
    ): List<ClassDependencyAndroid> {
        if (!classType.requiresDependency()) return emptyList()

        val dependencyType = classType.dependencyType() ?: return emptyList()
        val availableModules = getAvailableModules(currentModule)

        return availableModules
            .filter { module -> hasClassType(module, dependencyType) }
            .map { module -> ClassDependencyAndroid(dependencyType, NameMappings.moduleName(module.id)) }
    }

    private fun getAvailableModules(currentModule: ProjectGraph): List<ProjectGraph> {
        val currentModuleNumber = currentModule.id.split("_").last().toInt()
        val currentLayer = currentModule.layer

        return currentModule.nodes.filter { node ->
            val otherModuleNumber = node.id.split("_").last().toInt()
            val otherLayer = node.layer

            // Only reference modules that:
            // 1. Are in the same layer but have a lower module number
            // 2. Are in a lower layer
            // 3. Have the required class types based on their module number
            ((node.layer == currentLayer && otherModuleNumber < currentModuleNumber) ||
                (otherLayer < currentLayer)) &&
                hasClassType(
                    node,
                    ClassTypeAndroid.REPOSITORY
                ) // Only include modules that have the required class type
        }
    }

    private fun hasClassType(module: ProjectGraph, classType: ClassTypeAndroid): Boolean {
        val moduleNumber = module.id.split("_").last().toInt()

        return when (classType) {
            ClassTypeAndroid.REPOSITORY -> moduleNumber % 4 == 0 && moduleNumber > 0
            ClassTypeAndroid.API -> moduleNumber % 4 == 0 && moduleNumber > 0
            ClassTypeAndroid.WORKER -> moduleNumber % 5 == 0 && moduleNumber > 0
            ClassTypeAndroid.VIEWMODEL -> true // All modules have ViewModels
            ClassTypeAndroid.SERVICE -> moduleNumber % 5 == 0 && moduleNumber > 0
            ClassTypeAndroid.USECASE -> moduleNumber % 3 == 0 && moduleNumber > 0
            else -> false
        }
    }
}
