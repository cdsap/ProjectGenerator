package io.github.cdsap.projectgenerator.generator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ModuleGenerationPortsTest {

    @Test
    fun `module generation ports live in the generator package`() {
        val expectedPackage = "io.github.cdsap.projectgenerator.generator"
        val ports = listOf(
            ModuleClassPlanner::class,
            ClassGenerator::class,
            TestGenerator::class,
            BuildFilesGenerator::class,
            ResourceGeneratorA::class
        )

        ports.forEach { port ->
            assertEquals(expectedPackage, port.java.packageName, "${port.simpleName} package")
        }
    }
}
