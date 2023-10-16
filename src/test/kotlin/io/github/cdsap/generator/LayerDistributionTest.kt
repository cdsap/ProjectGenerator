package io.github.cdsap.generator

import io.github.cdsap.generator.model.Shape
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LayerDistributionTest {

    @Test
    fun testRhombusShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100)
        val expected = listOf(15, 20, 30, 20, 15)
        val result = layerDistribution.get(Shape.RHOMBUS)
        assertEquals(expected, result)
    }

    @Test
    fun testMiddleBottleneckShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100)
        val expected = listOf(35, 15, 2, 20, 30)
        val result = layerDistribution.get(Shape.MIDDLE_BOTTLENECK)
        assertEquals(expected, result)
    }

    @Test
    fun testRectangleShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100)
        val expected = listOf(20, 20, 20, 20, 20)
        val result = layerDistribution.get(Shape.RECTANGLE)
        assertEquals(expected, result)
    }

    @Test
    fun testTriangleShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100)
        val expected = listOf(5, 10, 15, 30, 40)
        val result = layerDistribution.get(Shape.TRIANGLE)
        assertEquals(expected, result)
    }

    @Test
    fun testFlatShape() {
        val layerDistribution = LayerDistribution(numberOfModules = 100)
        val expected = listOf(100)
        val result = layerDistribution.get(Shape.FLAT)
        assertEquals(expected, result)
    }
}
