package io.github.cdsap.projectgenerator.generator.test

import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryAndroid
import io.github.cdsap.projectgenerator.generator.TestGenerator
import io.github.cdsap.projectgenerator.model.ClassDefinitionAndroid
import io.github.cdsap.projectgenerator.model.ClassDependencyAndroid
import io.github.cdsap.projectgenerator.model.ClassTypeAndroid
import io.github.cdsap.projectgenerator.model.ModuleClassDefinitionAndroid
import java.io.File


class TestGeneratorAndroid : TestGenerator<ModuleClassDefinitionAndroid, GenerateDictionaryAndroid> {


    override fun generate(
        moduleDefinition: ModuleClassDefinitionAndroid,
        projectName: String,
        classesDictionary: MutableMap<String, MutableList<GenerateDictionaryAndroid>>
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
        moduleDefinition: ModuleClassDefinitionAndroid,
        classDefinition: ClassDefinitionAndroid,
        classesDictionary: MutableMap<String, MutableList<GenerateDictionaryAndroid>>
    ): String {

        val className = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}"
        val testContent = when (classDefinition.type) {
            ClassTypeAndroid.REPOSITORY -> generateRepositoryTest(moduleDefinition, classDefinition, classesDictionary)
            ClassTypeAndroid.API -> generateApiTest(className)
            ClassTypeAndroid.VIEWMODEL -> generateViewModelTest(
                className,
                classDefinition.dependencies,
                classesDictionary
            )

            ClassTypeAndroid.WORKER -> generateWorkerTest(className)
            ClassTypeAndroid.ACTIVITY -> generateActivityTest(className)
            ClassTypeAndroid.FRAGMENT -> generateFragmentTest(className)
            ClassTypeAndroid.SERVICE -> generateServiceTest(className)
            ClassTypeAndroid.STATE -> generateStateTest(className)
            ClassTypeAndroid.MODEL -> generateModelTest(className)
            ClassTypeAndroid.USECASE -> generateUseCaseTest(moduleDefinition, className)
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
                ClassTypeAndroid.WORKER -> {
                    appendLine("import androidx.work.testing.TestWorkerBuilder")
                    appendLine("import androidx.work.Worker")
                    appendLine("import androidx.work.WorkerParameters")
                    appendLine("import androidx.test.core.app.ApplicationProvider")
                    appendLine("import android.content.Context")
                    appendLine("import androidx.work.testing.TestListenableWorkerBuilder")
                    appendLine("import androidx.work.CoroutineWorker")
                }

                ClassTypeAndroid.USECASE -> {
                    appendLine("import kotlinx.coroutines.flow.first")
                }

                ClassTypeAndroid.VIEWMODEL -> {
                    classDefinition.dependencies.forEach { dep ->
                        val s = classesDictionary.filter { it.key == dep.sourceModuleId }
                        if (s.isNotEmpty()) {
                            val x = s.values.flatten().first { it.type == ClassTypeAndroid.REPOSITORY }
                            if (x != null) {
                                val repository = x.className
                                appendLine("import com.awesomeapp.${dep.sourceModuleId}.$repository")
                            }
                        }
                    }
                }

                ClassTypeAndroid.REPOSITORY -> {
                    classDefinition.dependencies.forEach { dep ->
                        val s = classesDictionary.filter { it.key == dep.sourceModuleId }
                        if (s.isNotEmpty()) {
                            val x = s.values.flatten().first { it.type == ClassTypeAndroid.API }
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
        moduleDefinition: ModuleClassDefinitionAndroid,
        classDefinition: ClassDefinitionAndroid,
        a: MutableMap<String, MutableList<GenerateDictionaryAndroid>>
    ): String {
        val xa = mutableListOf<String>()

        if (classDefinition.dependencies.isNotEmpty()) {
            classDefinition.dependencies.mapIndexed { index, dep ->
                val s = a.filter { it.key == dep.sourceModuleId }
                if (s.isNotEmpty()) {
                    val x = s.values.flatten().first { it.type == ClassTypeAndroid.API }
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

    private fun generateViewModelTest(
        className: String,
        dependencies: List<ClassDependencyAndroid>,
        a: MutableMap<String, MutableList<GenerateDictionaryAndroid>>
    ): String {
        val xa = mutableListOf<String>()
        val xc = mutableListOf<String>()
        dependencies.mapIndexed { index, dep ->
            val s = a.filter { it.key == dep.sourceModuleId }
            if (s.isNotEmpty()) {
                val x = s.values.flatten().first { it.type == ClassTypeAndroid.REPOSITORY }
                if (x != null) {
                    val repository = x.className
                    xa.add("repository$index: $repository")
                }
            }
        }

        val constructorParams = xa.joinToString(",\n        ")

        val xb = mutableListOf<String>()
        dependencies.mapIndexed { index, dep ->
            val s = a.filter { it.key == dep.sourceModuleId }
            if (s.isNotEmpty()) {
                val x = s.values.flatten().first { it.type == ClassTypeAndroid.REPOSITORY }
                if (x != null) {
                    val repository = x.className
                    xb.add("private lateinit var repository$index: $repository")
                }
            }
        }
        val mockRepositories = xb.joinToString("\n        ")

        dependencies.mapIndexed { index, dep ->

            val s = a.filter { it.key == dep.sourceModuleId }
            if (s.isNotEmpty()) {
                val x = s.values.flatten().first { it.type == ClassTypeAndroid.REPOSITORY }
                if (x != null) {
                    val repository = x.className
                    xc.add(
                        "repository$index = $repository(${
                            x.dependencies.mapIndexed { depIndex, innerDep ->
                                val innerS = a.filter { it.key == innerDep.sourceModuleId }
                                if (innerS.isNotEmpty()) {
                                    val innerX = innerS.values.flatten().first { it.type == ClassTypeAndroid.API }
                                    if (innerX != null) {
                                        "com.awesomeapp.${innerDep.sourceModuleId}.${innerX.className}()"
                                    } else ""
                                } else ""
                            }.joinToString(", ")
                        })"
                    )

                }
            }
        }

        val testContent = if (constructorParams.isNotEmpty()) {
            """
            |    $mockRepositories
            |
            |    private lateinit var viewModel: $className
            |
            |    @Before
            |    fun setup() {
            |       ${xc.joinToString("\n        ")}
            |        viewModel = $className(
            |            ${dependencies.mapIndexed { index, _ -> "repository$index" }.joinToString(",\n            ")}
            |        )
            |    }
            |
            |    @Test
            |    fun `test state updates with data`() = runTest {
            |        assertNotNull(viewModel.state.value)
            |    }
            """.trimMargin()
        } else {
            """
            |    private lateinit var viewModel: $className
            |
            |    @Before
            |    fun setup() {
            |        viewModel = $className()
            |    }
            |
            |    @Test
            |    fun `test state updates with data`() = runTest {
            |        assertNotNull(viewModel.state.value)
            |    }
            """.trimMargin()
        }
        return testContent
    }

    private fun generateWorkerTest(className: String): String = """
        |    @Test
        |    fun `placeholder - worker should be tested in androidTest`() {
        |        // Workers depend on Android Context and should be tested with Instrumented tests (androidTest)
        |        assertTrue(true)
        |    }
    """.trimMargin()

    private fun generateActivityTest(className: String): String = """
        |    @Test
        |    fun `placeholder - activity should be tested in androidTest`() {
        |        // Activities should be tested with Instrumented tests (androidTest)
        |        assertTrue(true)
        |    }
    """.trimMargin()

    private fun generateFragmentTest(className: String): String = """
        |    @Test
        |    fun `placeholder - fragment should be tested in androidTest`() {
        |        // Fragments should be tested with Instrumented tests (androidTest)
        |        assertTrue(true)
        |    }
    """.trimMargin()

    private fun generateServiceTest(className: String): String = """
        |    @Test
        |    fun `placeholder - service should be tested in androidTest`() {
        |        // Services should be tested with Instrumented tests (androidTest)
        |        assertTrue(true)
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

    private fun generateUseCaseTest(moduleDefinition: ModuleClassDefinitionAndroid, className: String): String {
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
