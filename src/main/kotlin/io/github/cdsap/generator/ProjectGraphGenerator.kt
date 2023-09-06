package io.github.cdsap.generator

import io.github.cdsap.generator.model.ProjectGraph
import kotlin.random.Random

class ProjectGraphGenerator(private val numberOfLayers: Int, private val distribution: List<Int>) {
    fun generate(): MutableList<ProjectGraph> {
        var generalCounter = 0
        var oldLayer = 0
        val nodes = mutableListOf<ProjectGraph>()
        for (i in 0 until numberOfLayers) {
            if (i == 0) {
                for (x in 0 until distribution[0]) {
                    generalCounter++
                    nodes.add(
                        ProjectGraph(
                            id = "module_${i}_$generalCounter",
                            layer = i,
                            nodes = emptyList()
                        )
                    )
                }
            } else {
                val random = generateRandomRelations(distribution[i], distribution[oldLayer])
                for (x in 0 until distribution[i]) {
                    val nodesLayer = nodes.filter { it.layer == oldLayer }
                    val elements = random[x]
                    val nodesConnected = mutableListOf<String>()
                    val nodesAlreadyUsed = mutableListOf<String>()
                    for (y in 0 until elements) {
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
                        nodesConnected.add(node)
                    }
                    generalCounter++
                    nodes.add(
                        ProjectGraph(
                            id = "module_${i}_$generalCounter",
                            layer = i,
                            nodes = nodesConnected
                        )
                    )
                }
            }

            oldLayer = i
        }
        generalCounter++
        oldLayer++
        // we need to create the main entry point module
        nodes.add(ProjectGraph(
            id = "module_${oldLayer}_$generalCounter",
            layer = oldLayer,
            nodes = nodes.filter { it.layer == oldLayer - 1 }.map { it.id }
        ))
        return nodes
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
}
