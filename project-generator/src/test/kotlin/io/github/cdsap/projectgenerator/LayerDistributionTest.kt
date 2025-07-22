package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.ClassesPerModuleType
import io.github.cdsap.projectgenerator.model.Shape
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class LayerDistributionTest {

    @Test
    fun testRhombusShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100, numberOfLayers = 5)
        val expected = listOf(12, 25, 25, 25, 12)
        val result = layerDistribution.get(Shape.RHOMBUS)
        assertEquals(expected, result)
    }

    @Test
    fun testMiddleBottleneckShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100, numberOfLayers = 5)
        val expected = listOf(16, 33, 1, 27, 14, 8)
        val result = layerDistribution.get(Shape.MIDDLE_BOTTLENECK)
        assertEquals(expected, result)
    }

    @Test
    fun testRectangleShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100, numberOfLayers = 5)
        val expected = listOf(20, 20, 20, 20, 20)
        val result = layerDistribution.get(Shape.RECTANGLE)
        assertEquals(expected, result)
    }

    @Test
    fun testTriangleShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100, numberOfLayers = 5)
        val expected = listOf(44, 22, 15, 11, 8)
        val result = layerDistribution.get(Shape.TRIANGLE)
        assertEquals(expected, result)
    }

    @Test
    fun testFlatShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100, numberOfLayers = 5)
        val expected = listOf(100)
        val result = layerDistribution.get(Shape.FLAT)
        assertEquals(expected, result)
    }

    @ParameterizedTest
    @EnumSource(Shape::class)
    fun testCorrectAmountOfDistributionsIsReturned(shape: Shape) {

        val layerDistribution = LayerDistribution(numberOfModules = 1800, numberOfLayers = 30)
        val result = layerDistribution.get(shape)
        if (shape == Shape.FLAT) {
            assertEquals(1, result.size)

        } else {
            assertEquals(30, result.size)
        }

        val layerDistributionOdd = LayerDistribution(numberOfModules = 1801, numberOfLayers = 30)
        val resultOdd = layerDistributionOdd.get(shape)
        if (shape == Shape.FLAT) {
            assertEquals(1, resultOdd.size)

        } else {
            assertEquals(30, result.size)
        }
    }
}
