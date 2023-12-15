package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.Shape

class LayerDistribution(private val numberOfModules: Int, private val numberOfLayers: Int) {

    fun get(shape: Shape): List<Int> {
        val distributions = Distributions()
        return when (shape) {
            Shape.RHOMBUS -> {
                distributions.distributeModulesForRhombus(numberOfModules,numberOfLayers)
            }
            Shape.MIDDLE_BOTTLENECK -> {
                distributions.distributeModulesForRhombusInverse(numberOfModules,numberOfLayers)
            }

            Shape.RECTANGLE -> {
                distributions.distributeModulesEqually(numberOfModules,numberOfLayers)
            }

            Shape.TRIANGLE -> {
                distributions.distributeModulesHarmonically(numberOfModules,numberOfLayers)
            }

            Shape.INVERSE_TRIANGLE -> {
                distributions.distributeModulesHarmonically(numberOfModules,numberOfLayers).reversed()
            }

            Shape.FLAT -> {
                listOf((numberOfModules * 1.0).toInt())
            }

        }
    }
}
