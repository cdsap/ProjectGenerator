package io.github.cdsap.generator

import io.github.cdsap.generator.model.Shape

class LayerDistribution(private val numberOfModules: Int, private val numberOfLayers: Int = 5) {

    fun get(shape: Shape): List<Int> {
        return when (shape) {
            Shape.RHOMBUS -> {
                listOf(
                    (numberOfModules * 0.15).toInt(),
                    (numberOfModules * 0.20).toInt(),
                    (numberOfModules * 0.30).toInt(),
                    (numberOfModules * 0.20).toInt(),
                    (numberOfModules * 0.15).toInt()
                )
            }

            Shape.MIDDLE_BOTTLENECK -> {
                listOf(
                    (numberOfModules * 0.35).toInt(),
                    (numberOfModules * 0.20).toInt(),
                    (numberOfModules * 0.02).toInt(),
                    (numberOfModules * 0.15).toInt(),
                    (numberOfModules * 0.30).toInt()
                )
            }

            Shape.RECTANGLE -> {
                val distributionValue = (numberOfModules.toDouble() / (numberOfLayers))
                listOf(
                    (distributionValue).toInt(),
                    (distributionValue).toInt(),
                    (distributionValue).toInt(),
                    (distributionValue).toInt(),
                    (distributionValue).toInt(),
                )
            }


            Shape.TRIANGLE -> {

                listOf(
                    (numberOfModules * 0.40).toInt(),
                    (numberOfModules * 0.30).toInt(),
                    (numberOfModules * 0.15).toInt(),
                    (numberOfModules * 0.10).toInt(),
                    (numberOfModules * 0.05).toInt()
                )
            }

            Shape.FLAT -> {
                listOf((numberOfModules * 1.0).toInt())
            }

        }
    }
}

