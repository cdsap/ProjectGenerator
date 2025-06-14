package io.github.cdsap.projectgenerator.generator.test

import io.github.cdsap.projectgenerator.writer.TestGenerator
import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryJvm
import io.github.cdsap.projectgenerator.model.ClassDefinitionJvm
import io.github.cdsap.projectgenerator.model.ClassTypeJvm
import io.github.cdsap.projectgenerator.model.ModuleClassDefinitionJvm
import java.io.File


class TestGeneratorJvm : TestGenerator<ModuleClassDefinitionJvm, GenerateDictionaryJvm> {

    override fun generate(
        moduleDefinition: ModuleClassDefinitionJvm,
        projectName: String,
        classesDictionary: MutableMap<String, MutableList<GenerateDictionaryJvm>>
    ) {
        val testDir =
            File("$projectName/layer_${moduleDefinition.layer}/${moduleDefinition.moduleId}/src/test/kotlin/com/awesomeapp/${moduleDefinition.moduleId}/")
        testDir.mkdirs()

        moduleDefinition.classes.forEach { classDefinition ->
            val testFileName = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}Test.kt"
            val testContent = generateTestContent(moduleDefinition, classDefinition, classesDictionary)
            File(testDir, testFileName).writeText(testContent)
        }
    }

    private fun generateTestContent(
        moduleDefinition: ModuleClassDefinitionJvm,
        classDefinition: ClassDefinitionJvm,
        a: MutableMap<String, MutableList<GenerateDictionaryJvm>>
    ): String {
        val className = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}"
        val testContent = when (classDefinition.type) {
            ClassTypeJvm.REPOSITORY -> generateRepositoryTest(moduleDefinition, classDefinition, a)
            ClassTypeJvm.API -> generateApiTest(className)
            ClassTypeJvm.STATE -> generateStateTest(className)
            ClassTypeJvm.MODEL -> generateModelTest(className)
            ClassTypeJvm.USECASE -> generateUseCaseTest(moduleDefinition, className)
            else -> ""
        }

        val imports = buildString {
            appendLine("import org.junit.Test")
            appendLine("import org.junit.Before")
            appendLine("import org.junit.runner.RunWith")
            appendLine("import org.junit.runners.JUnit4")
            appendLine("import org.junit.Rule")
            appendLine("import kotlinx.coroutines.test.runTest")
            appendLine("import kotlinx.coroutines.ExperimentalCoroutinesApi")
            appendLine("import org.junit.Assert.*")
            appendLine("import kotlin.test.assertTrue")
            appendLine("import kotlin.test.assertNotNull")
            appendLine("import kotlin.test.assertEquals")
            appendLine("import kotlin.test.assertFalse")
            appendLine("import com.awesomeapp.${moduleDefinition.moduleId}.*")

            // Add specific imports based on class type
            when (classDefinition.type) {
                ClassTypeJvm.USECASE -> {
                    appendLine("import kotlinx.coroutines.flow.first")
                }

                ClassTypeJvm.REPOSITORY -> {
                    classDefinition.dependencies.forEach { dep ->
                        val s = a.filter { it.key == dep.sourceModuleId }
                        if (s.isNotEmpty()) {
                            val x = s.values.flatten().first { it.type == ClassTypeJvm.API }
                            if (x != null) {
                                val repository = x.className
                                appendLine("import com.awesomeapp.${dep.sourceModuleId}.$repository")
                            }
                        }
                    }
                }

                else -> {}
            }
        }

        val classAnnotations = "@OptIn(ExperimentalCoroutinesApi::class)"

        return """
            |package com.awesomeapp.${moduleDefinition.moduleId}
            |
            |$imports
            |
            |$classAnnotations
            |class ${className}Test {
            |    $testContent
            |}
        """.trimMargin()
    }

    private fun generateRepositoryTest(
        moduleDefinition: ModuleClassDefinitionJvm,
        classDefinition: ClassDefinitionJvm,
        a: MutableMap<String, MutableList<GenerateDictionaryJvm>>
    ): String {
        val xa = mutableListOf<String>()

        if (classDefinition.dependencies.isNotEmpty()) {
            classDefinition.dependencies.mapIndexed { index, dep ->
                val s = a.filter { it.key == dep.sourceModuleId }
                if (s.isNotEmpty()) {
                    val x = s.values.flatten().first { it.type == ClassTypeJvm.API }
                    if (x != null) {
                        val apiClassName = x.className
                        xa.add("$apiClassName()")
                    }
                }
            }
        } else ""

        val constructorParams = xa.joinToString(",\n            ")
        val testContent = """
            |    @Before
            |    fun setup() {
            |    }
            |
            |    @Test
            |    fun `test getData returns data`() = runTest {
            |        val result = ${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}($constructorParams).getData()
            |        assertNotNull(result)
            |    }
        """.trimMargin()

        return testContent
    }

    private fun generateApiTest(className: String): String = """
        |    private lateinit var api: $className
        |
        |    @Before
        |    fun setup() {
        |        api = $className()
        |    }
        |
        |    @Test
        |    fun `test fetchData returns data`() = runTest {
        |        val result = api.fetchData()
        |        assertNotNull(result)
        |    }
    """.trimMargin()

    private fun generateStateTest(className: String): String = """
        |    @Test
        |    fun `test loading state`() {
        |        val state = $className.Loading
        |        assertNotNull(state)
        |    }
        |
        |    @Test
        |    fun `test success state`() {
        |        val state = $className.Success("test data")
        |        assertNotNull(state)
        |        assertEquals("test data", (state as $className.Success).data)
        |    }
        |
        |    @Test
        |    fun `test error state`() {
        |        val state = $className.Error("test error")
        |        assertNotNull(state)
        |        assertEquals("test error", (state as $className.Error).message)
        |    }
    """.trimMargin()

    private fun generateModelTest(className: String): String = """
        |    @Test
        |    fun `test model creation`() {
        |        val model = $className()
        |        assertNotNull(model)
        |    }
    """.trimMargin()

    private fun generateUseCaseTest(moduleDefinition: ModuleClassDefinitionJvm, className: String): String {
        return """
            |    private lateinit var useCase: $className
            |
            |    @Before
            |    fun setup() {
            |        useCase = $className()
            |    }
            |
            |    @Test
            |    fun `test invoke returns data`() = runTest {
            |        val result = useCase().first()
            |        assertEquals("Data from $className UseCase", result)
            |    }
        """.trimMargin()
    }


}
