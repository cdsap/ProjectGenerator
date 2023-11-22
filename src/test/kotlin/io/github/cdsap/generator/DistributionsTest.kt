package io.github.cdsap.generator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DistributionsTest {

    private val distributions = Distributions()

    @Test
    fun `test distributeModulesForRhombusInverse with even modules and layers`() {
        val result = distributions.distributeModulesForRhombusInverse(100, 7)
        assertEquals(listOf(8, 14, 27, 1, 24, 12, 8, 5), result)
    }

    @Test
    fun `test distributeModulesForRhombusInverse with even layers`() {
        val result = distributions.distributeModulesForRhombusInverse(253, 9)
        assertEquals(listOf(61, 30, 20, 15, 1, 15, 20, 30, 61), result)
    }

    @Test
    fun `test distributeModulesForRhombusInverse with odd modules and layers`() {
        val result = distributions.distributeModulesForRhombus(100, 7)
        assertEquals(listOf(6, 10, 21, 25, 21, 10, 6), result)
    }

    @Test
    fun `test distributeModulesForRhombus with even layers`() {
        val result = distributions.distributeModulesForRhombus(95, 8)
        assertEquals(listOf(6, 10, 20, 23, 18, 9, 5, 4), result)
    }

    @Test
    fun `test distributeModulesHarmonically`() {
        val result = distributions.distributeModulesHarmonically(10, 3)
        assertEquals(listOf(6, 3, 1), result)
    }

    @Test
    fun `test distributeModulesEqually with no remainder`() {
        val result = distributions.distributeModulesEqually(10, 2)
        assertEquals(listOf(5, 5), result)
    }

    @Test
    fun `test distributeModulesEqually with remainder`() {
        val result = distributions.distributeModulesEqually(10, 3)
        assertEquals(listOf(4, 3, 3), result)
    }
}
