package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.Shape
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
}
