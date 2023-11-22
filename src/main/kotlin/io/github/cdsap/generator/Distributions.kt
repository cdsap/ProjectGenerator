package io.github.cdsap.generator

class Distributions {

    fun distributeModulesForRhombusInverse(numberOfModules: Int, numberOfLayers: Int): List<Int> {
        val center = 1
        val halfN = (numberOfLayers - center) / 2

        return if (numberOfModules % 2 == 0) {
            val halfDistributionUp = distributeModulesHarmonically((numberOfModules - center) / 2, halfN).reversed()
            val halfDistributionDown =
                distributeModulesHarmonically((numberOfModules - center) / 2, halfN + 1).reversed()
            halfDistributionUp + center + halfDistributionDown.reversed()

        } else {
            val halfDistribution = distributeModulesHarmonically((numberOfModules - center) / 2, halfN)
            halfDistribution + center + halfDistribution.reversed()
        }
    }

    fun distributeModulesForRhombus(numberOfModules: Int, numberOfLayers: Int): List<Int> {
        val center = (numberOfModules) / 4
        val halfN = (numberOfLayers - 1) / 2
        val halfX = (numberOfModules - center) / 2
        return if (numberOfLayers % 2 == 0) {
            val halfDistributionUp = distributeModulesHarmonically(halfX, halfN).reversed()
            val halfDistributionDown = distributeModulesHarmonically(halfX, halfN + 1).reversed()
            halfDistributionUp + center + halfDistributionDown.reversed()

        } else {
            val halfDistribution = distributeModulesHarmonically(halfX, halfN).reversed()
            halfDistribution + center + halfDistribution.reversed()
        }
    }

    fun distributeModulesHarmonically(numberOfModules: Int, numberOfLayers: Int): List<Int> {
        val weights = (1..numberOfLayers).map { 1.0 / it }
        val totalWeight = weights.sum()

        val distribution = weights.map { (numberOfModules * (it / totalWeight)).toInt() }.toMutableList()

        var distributed = distribution.sum()
        var layer = 0
        while (distributed < numberOfModules) {
            distribution[layer % numberOfLayers]++
            distributed++
            layer++
        }

        return distribution
    }

    fun distributeModulesEqually(numberOfModules: Int, numberOfLayers: Int): List<Int> {
        val baseModulesPerLayer = numberOfModules / numberOfLayers
        val extraModules = numberOfModules % numberOfLayers

        val distribution = MutableList(numberOfLayers) { baseModulesPerLayer }
        for (i in 0 until extraModules) {
            distribution[i]++
        }
        return distribution
    }
}
