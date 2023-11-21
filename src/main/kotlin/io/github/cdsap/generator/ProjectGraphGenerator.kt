package io.github.cdsap.generator

import io.github.cdsap.generator.model.ClassesPerModule
import io.github.cdsap.generator.model.ClassesPerModuleType
import io.github.cdsap.generator.model.ProjectGraph
import io.github.cdsap.generator.model.TypeProject
import io.github.cdsap.generator.model.TypeProjectRequested
import kotlin.random.Random

class ProjectGraphGenerator(
    private val numberOfLayers: Int,
    private val distribution: List<Int>,
    private val typeOfProjectRequested: TypeProjectRequested,
    private val classesPerModule: ClassesPerModule
) {
    fun generate(): MutableList<ProjectGraph> {
        var generalCounter = 0
        var oldLayer = 0
        val nodes = mutableListOf<ProjectGraph>()
        println(distribution)
        for (i in 0 until numberOfLayers) {
            if (i == 0) {
                for (x in 0 until distribution[0]) {
                    generalCounter++
                    nodes.add(
                        ProjectGraph(
                            id = "module_${i}_$generalCounter",
                            layer = i,
                            nodes = emptyList(),
                            type = lib(typeOfProjectRequested),
                            classes = getClasses()
                        )
                    )
                }
            } else {
                println(numberOfLayers)
                println(distribution[i])
                println(distribution[oldLayer])
                val random = generateRandomRelations(distribution[i], distribution[oldLayer])

                for (x in 0 until distribution[i]) {
                    val nodesLayer = nodes.filter { it.layer == oldLayer }
                    val elements = random[x]
                    val nodesConnected = mutableListOf<String>()
                    val nodesAlreadyUsed = mutableListOf<String>()
                    for (y in 0 until elements) {
                        var node = assignNode(nodesLayer, nodesAlreadyUsed, oldLayer)
                        nodesConnected.add(node)
                    }
                    generalCounter++
                    nodes.add(
                        ProjectGraph(
                            id = "module_${i}_$generalCounter",
                            layer = i,
                            nodes = nodesConnected.flatMap { node -> listOf(nodes.first { it.id == node }) },
                            type = lib(typeOfProjectRequested),
                            classes = getClasses()
                        )
                    )
                }
            }

            oldLayer = i
        }
        generalCounter++
        oldLayer++
        // we need to create the main entry point module
        nodes.add(
            ProjectGraph(
                id = "module_${oldLayer}_$generalCounter",
                layer = oldLayer,
                nodes = nodes.filter { it.layer == oldLayer - 1 }.map { it.id }
                    .flatMap { node -> listOf(nodes.first { it.id == node }) },
                type = mainEntryPoint(typeOfProjectRequested),
                classes = getClasses()
            )
        )
        return nodes
    }

    private fun assignNode(
        nodesLayer: List<ProjectGraph>,
        nodesAlreadyUsed: MutableList<String>,
        oldLayer: Int
    ): String {
        var node = nodesLayer.random().id
        if (nodesAlreadyUsed.isEmpty()) {
            nodesAlreadyUsed.add(node)
        } else {
            while (nodesAlreadyUsed.contains(node) && distribution[oldLayer] != 1) {
                node = nodesLayer.random().id
            }
            if (!nodesAlreadyUsed.contains(node)) {
                nodesAlreadyUsed.add(node)
            }
        }
        return node
    }

    private fun getClasses() = if (classesPerModule.type == ClassesPerModuleType.FIXED) {
        classesPerModule.classes
    } else {
        Random.nextInt(2, classesPerModule.classes)
    }

    private fun generateRandomRelations(numberModules: Int, numberModulesUpperLayer: Int): List<Int> {
        val listOfModules = mutableListOf<Int>()

        if (numberModules == 1) {
            listOfModules.add(numberModulesUpperLayer)
            return listOfModules
        } else {

            for (i in 0 until numberModules) {
                if (numberModulesUpperLayer == 1) {
                    listOfModules.add(1)
                } else {
                    var next = Random.nextInt(1, numberModulesUpperLayer)
                    listOfModules.add(next)
                }
            }
            return listOfModules
        }
    }

    private fun lib(typeOfProject: TypeProjectRequested): TypeProject {
        return if (typeOfProject == TypeProjectRequested.ANDROID) {
            TypeProject.ANDROID_LIB
        } else {
            TypeProject.LIB
        }
    }

    private fun mainEntryPoint(typeOfProject: TypeProjectRequested): TypeProject {
        return if (typeOfProject == TypeProjectRequested.ANDROID) {
            TypeProject.ANDROID_APP
        } else {
            TypeProject.LIB
        }
    }
}
