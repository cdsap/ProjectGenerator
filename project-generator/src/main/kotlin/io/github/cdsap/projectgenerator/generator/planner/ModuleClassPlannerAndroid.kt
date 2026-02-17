package io.github.cdsap.projectgenerator.generator.planner

import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.writer.ModuleClassPlanner

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

        val localDep = { type: ClassTypeAndroid ->
            mutableListOf(ClassDependencyAndroid(type, moduleId))
        }

        val baseClasses = listOf(
            ClassTypeAndroid.ENTITY,
            ClassTypeAndroid.MODEL,
            ClassTypeAndroid.DATABASE,
            ClassTypeAndroid.DAO,
            ClassTypeAndroid.REPOSITORY,
            ClassTypeAndroid.USECASE,
            ClassTypeAndroid.STATE,
            ClassTypeAndroid.VIEWMODEL,
            ClassTypeAndroid.SCREEN
        )

        baseClasses.forEach { type ->
            val deps = when (type) {
                ClassTypeAndroid.REPOSITORY -> localDep(ClassTypeAndroid.DAO)
                ClassTypeAndroid.USECASE -> localDep(ClassTypeAndroid.REPOSITORY)
                ClassTypeAndroid.VIEWMODEL -> localDep(ClassTypeAndroid.USECASE)
                ClassTypeAndroid.SCREEN -> localDep(ClassTypeAndroid.VIEWMODEL)
                else -> mutableListOf()
            }
            classes.add(ClassDefinitionAndroid(type = type, index = currentIndex++, dependencies = deps))
        }

        if (projectGraph.type == TypeProject.ANDROID_APP) {
            classes.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.ACTIVITY,
                    index = currentIndex++,
                    dependencies = mutableListOf()
                )
            )
            classes.add(
                ClassDefinitionAndroid(
                    type = ClassTypeAndroid.FRAGMENT,
                    index = currentIndex++,
                    dependencies = mutableListOf()
                )
            )
        }

        val remainingClasses = projectGraph.classes - classes.size
        if (remainingClasses > 0) {
            for (i in 1..remainingClasses) {
                val classType = when (i % 2) {
                    0 -> ClassTypeAndroid.MODEL
                    else -> ClassTypeAndroid.STATE
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
        return listOf(ClassDependencyAndroid(dependencyType, NameMappings.moduleName(currentModule.id)))
    }
}
