package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.ClassesPerModuleType
import io.github.cdsap.projectgenerator.model.Shape
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ProjectGraphGeneratorTest {

    @ParameterizedTest
    @EnumSource(Shape::class)
    fun `generates every shape at the minimum module count`(shape: Shape) {
        val layers = 5
        val distribution = LayerDistribution(numberOfModules = layers + 1, numberOfLayers = layers).get(shape)

        val nodes = ProjectGraphGenerator(
            if (shape == Shape.FLAT) 1 else layers,
            distribution,
            TypeProjectRequested.JVM,
            ClassesPerModule(ClassesPerModuleType.FIXED, 10)
        ).generate()

        assertTrue(distribution.all { it > 0 })
        assertEquals(distribution.sum() + 1, nodes.size)
        assertEquals(if (shape == Shape.FLAT) 1 else layers, nodes.last().layer)
    }

    @Test
    fun `generates a graph when the previous layer is empty`() {
        val nodes = ProjectGraphGenerator(
            numberOfLayers = 3,
            distribution = listOf(1, 0, 1),
            typeOfProjectRequested = TypeProjectRequested.JVM,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10)
        ).generate()

        assertTrue(nodes.single { it.layer == 2 }.nodes.isEmpty())
    }

    @Test
    fun testLayerHasRelationWithPreviousLayer() {
        val numberOfLayers = 5
        val distribution = listOf(3, 5, 2, 4, 6)
        val typeOfProjectRequested = TypeProjectRequested.ANDROID
        val nodes = ProjectGraphGenerator(
            numberOfLayers,
            distribution,
            typeOfProjectRequested,
            ClassesPerModule(ClassesPerModuleType.FIXED, 10)
        ).generate()
        assertTrue(nodes.filter { it.layer == 1 }[0].nodes.any { it.id.contains("module_0") })
        assertTrue(nodes.filter { it.layer == 2 }[0].nodes.any { it.id.contains("module_1") })
        assertTrue(nodes.filter { it.layer == 3 }[0].nodes.any { it.id.contains("module_2") })
        assertTrue(nodes.filter { it.layer == 4 }[0].nodes.any { it.id.contains("module_3") })
    }

    @Test
    fun testMainEntryPointHasRelationWithPreviousLayer() {
        val numberOfLayers = 5
        val distribution = listOf(3, 5, 2, 4, 6)
        val typeOfProjectRequested = TypeProjectRequested.ANDROID
        val nodes = ProjectGraphGenerator(
            numberOfLayers, distribution, typeOfProjectRequested,
            ClassesPerModule(ClassesPerModuleType.FIXED, 10)
        ).generate()
        assertTrue(nodes.filter { it.layer == 5 }.size == 1)
        assertTrue(nodes.filter { it.layer == 5 }[0].nodes.size == nodes.filter { it.layer == 4 }.size)
    }
}
