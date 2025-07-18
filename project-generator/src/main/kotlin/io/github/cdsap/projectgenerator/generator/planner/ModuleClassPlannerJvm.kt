package io.github.cdsap.projectgenerator.generator.planner

import io.github.cdsap.projectgenerator.writer.ModuleClassPlanner
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.NameMappings

/**
 * Plans what classes each module should have and their dependencies
 */
class ModuleClassPlannerJvm : ModuleClassPlanner<ModuleClassDefinitionJvm> {
   override fun planModuleClasses(projectGraph: ProjectGraph): ModuleClassDefinitionJvm {
        val moduleId = NameMappings.moduleName(projectGraph.id)
        val layer = projectGraph.layer
        val moduleNumber = projectGraph.id.split("_").last().toInt()
        val classes = mutableListOf<ClassDefinitionJvm>()
        var currentIndex = 1

        // First, add all core classes in the correct order
        val coreClasses = mutableListOf<ClassDefinitionJvm>()

        // Every module gets a ViewModel
        coreClasses.add(ClassDefinitionJvm(
            type = ClassTypeJvm.USECASE,
            index = currentIndex++,
            dependencies = findDependenciesForClass(ClassTypeJvm.USECASE, projectGraph).toMutableList()
        ))

        // If we have more than 1 class, add Activity
        if (projectGraph.classes > 1) {
            coreClasses.add(ClassDefinitionJvm(
                type = ClassTypeJvm.REPOSITORY,
                index = currentIndex++,
                dependencies = mutableListOf() // Activity doesn't need Fragment dependency yet
            ))
        }

        // If we have more than 2 classes, add Compose Activity
        if (projectGraph.classes > 2) {
            coreClasses.add(ClassDefinitionJvm(
                type = ClassTypeJvm.API,
                index = currentIndex++,
                dependencies = mutableListOf() // Compose Activity doesn't need Fragment dependency yet
            ))
        }

        // If we have more than 3 classes, add Fragment
        if (projectGraph.classes > 3) {
            coreClasses.add(ClassDefinitionJvm(
                type = ClassTypeJvm.STATE,
                index = currentIndex++,
                dependencies = findDependenciesForClass(ClassTypeJvm.STATE, projectGraph).toMutableList()
            ))
        }

        // If we have more than 4 classes and it's a multiple of 4, add Repository and API
        if (projectGraph.classes > 4 && moduleNumber % 4 == 0) {
            coreClasses.add(ClassDefinitionJvm(
                type = ClassTypeJvm.MODEL,
                index = currentIndex++,
                dependencies = findDependenciesForClass(ClassTypeJvm.MODEL, projectGraph).toMutableList()
            ))

        }

        // Add core classes to the final list
        classes.addAll(coreClasses)

        // Add additional classes to reach the requested number
        val remainingClasses = projectGraph.classes - classes.size
        if (remainingClasses > 0) {
            // Create additional classes with simpler types that don't require dependencies
            for (i in 1..remainingClasses) {
                val classType = when (i % 3) {
                    0 -> ClassTypeJvm.STATE
                    1 -> ClassTypeJvm.MODEL
                    else -> ClassTypeJvm.API
                }

                classes.add(ClassDefinitionJvm(
                    type = classType,
                    index = currentIndex++,
                    dependencies = mutableListOf()
                ))
            }
        }


        // Extract the module IDs from node dependencies for proper Hilt module dependencies
        val dependencies = projectGraph.nodes.map { NameMappings.moduleName(it.id) }

        return ModuleClassDefinitionJvm(
            moduleId = moduleId,
            layer = layer,
            moduleNumber = moduleNumber,
            classes = classes,
            dependencies = dependencies
        )
    }

    private fun findDependenciesForClass(classType: ClassTypeJvm, currentModule: ProjectGraph): List<ClassDependencyJvm> {
        if (!classType.requiresDependency()) return emptyList()

        val dependencyType = classType.dependencyType() ?: return emptyList()
        val availableModules = getAvailableModules(currentModule)

        return availableModules
            .filter { module -> hasClassType(module, dependencyType) }
            .map { module -> ClassDependencyJvm(dependencyType, NameMappings.moduleName(module.id)) }
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
                hasClassType(node, ClassTypeJvm.REPOSITORY) // Only include modules that have the required class type
        }
    }

    private fun hasClassType(module: ProjectGraph, classType: ClassTypeJvm): Boolean {
        val moduleNumber = module.id.split("_").last().toInt()

        return when (classType) {
            ClassTypeJvm.USECASE -> moduleNumber % 4 == 0 && moduleNumber > 0
            ClassTypeJvm.REPOSITORY -> moduleNumber % 4 == 0 && moduleNumber > 0
            ClassTypeJvm.API -> moduleNumber % 5 == 0 && moduleNumber > 0
            ClassTypeJvm.MODEL -> true // All modules have ViewModels
            ClassTypeJvm.STATE -> moduleNumber % 5 == 0 && moduleNumber > 0
            else -> false
        }
    }
}
