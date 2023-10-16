package io.github.cdsap.generator

import io.github.cdsap.generator.model.TypeProjectRequested
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProjectGraphGeneratorTest {

    @Test
    fun testLayerHasRelationWithPreviousLayer() {
        val numberOfLayers = 5
        val distribution = listOf(3, 5, 2, 4, 6)
        val typeOfProjectRequested = TypeProjectRequested.ANDROID
        val nodes = ProjectGraphGenerator(numberOfLayers, distribution, typeOfProjectRequested).generate()
        assertTrue(nodes.filter { it.layer == 1 }[0].nodes.any { it.contains("module_0") })
        assertTrue(nodes.filter { it.layer == 2 }[0].nodes.any { it.contains("module_1") })
        assertTrue(nodes.filter { it.layer == 3 }[0].nodes.any { it.contains("module_2") })
        assertTrue(nodes.filter { it.layer == 4 }[0].nodes.any { it.contains("module_3") })
    }

    @Test
    fun testMainEntryPointHasRelationWithPreviousLayer() {
        val numberOfLayers = 5
        val distribution = listOf(3, 5, 2, 4, 6)
        val typeOfProjectRequested = TypeProjectRequested.ANDROID
        val nodes = ProjectGraphGenerator(numberOfLayers, distribution, typeOfProjectRequested).generate()
        assertTrue(nodes.filter { it.layer == 5 }.size == 1)
        assertTrue(nodes.filter { it.layer == 5 }[0].nodes.size == nodes.filter { it.layer == 4 }.size)
    }
}
