package io.github.cdsap.projectgenerator.generator.test

import io.github.cdsap.projectgenerator.generator.android.AndroidSourceSetLayout
import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryAndroid
import io.github.cdsap.projectgenerator.writer.TestGenerator
import io.github.cdsap.projectgenerator.model.ClassDefinitionAndroid
import io.github.cdsap.projectgenerator.model.ClassDependencyAndroid
import io.github.cdsap.projectgenerator.model.ClassTypeAndroid
import io.github.cdsap.projectgenerator.model.ModuleClassDefinitionAndroid
import io.github.cdsap.projectgenerator.NameMappings
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList


class TestGeneratorAndroid(
    private val kotlinMultiplatformLibrary: Boolean = false
) : TestGenerator<ModuleClassDefinitionAndroid, GenerateDictionaryAndroid> {


    override fun generate(
        moduleDefinition: ModuleClassDefinitionAndroid,
        projectName: String,
        classesDictionary: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ) {
        val layerDir = NameMappings.layerName(moduleDefinition.layer)
        val moduleDir = NameMappings.moduleName(moduleDefinition.moduleId)
        val packageDir = NameMappings.modulePackageName(moduleDefinition.moduleId)
        val testSourceDir = AndroidSourceSetLayout.kotlinTestSourceDir(
            moduleDefinition.projectType ?: io.github.cdsap.projectgenerator.model.TypeProject.ANDROID_LIB,
            kotlinMultiplatformLibrary
        )
        val testDir =
            File("$projectName/$layerDir/$moduleDir/$testSourceDir/com/awesomeapp/$packageDir/")
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
        classesDictionary: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {

        val className = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}"
        val testContent = when (classDefinition.type) {
            ClassTypeAndroid.REPOSITORY -> generateRepositoryTest(moduleDefinition, classDefinition, classesDictionary)
            ClassTypeAndroid.API -> generateApiTest(className)
            ClassTypeAndroid.ENTITY -> generateEntityTest(className)
            ClassTypeAndroid.DAO -> generateDaoTest(className)
            ClassTypeAndroid.DATABASE -> generateDatabaseTest(className)
            // todo generate correct tests for viewModel
//            ClassTypeAndroid.VIEWMODEL -> generateViewModelTest(
//                className,
//                classDefinition.dependencies,
//                classesDictionary
//            )

            ClassTypeAndroid.WORKER -> generateWorkerTest(className)
            ClassTypeAndroid.ACTIVITY -> generateActivityTest(className)
            ClassTypeAndroid.FRAGMENT -> generateFragmentTest(className)
            ClassTypeAndroid.SERVICE -> generateServiceTest(className)
            ClassTypeAndroid.STATE -> generateStateTest(className)
            ClassTypeAndroid.SCREEN -> generateScreenTest(className)
            ClassTypeAndroid.MODEL -> generateModelTest(className)
            ClassTypeAndroid.USECASE -> generateUseCaseTest(moduleDefinition, className, classesDictionary)
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
            appendLine("import com.awesomeapp.${NameMappings.modulePackageName(moduleDefinition.moduleId)}.*")

            // Add specific imports based on class type
            when (classDefinition.type) {
                ClassTypeAndroid.REPOSITORY, ClassTypeAndroid.USECASE -> {
                    appendLine("import androidx.room.Room")
                    appendLine("import androidx.test.core.app.ApplicationProvider")
                    appendLine("import android.content.Context")
                    appendLine("import kotlinx.coroutines.flow.first")
                    appendLine("import org.junit.After")
                    appendLine("import org.robolectric.RobolectricTestRunner")
                    appendLine("import org.robolectric.annotation.Config")
                }

                ClassTypeAndroid.WORKER -> {
                    appendLine("import androidx.work.testing.TestWorkerBuilder")
                    appendLine("import androidx.work.Worker")
                    appendLine("import androidx.work.WorkerParameters")
                    appendLine("import androidx.test.core.app.ApplicationProvider")
                    appendLine("import android.content.Context")
                    appendLine("import androidx.work.testing.TestListenableWorkerBuilder")
                    appendLine("import androidx.work.CoroutineWorker")
                }

               // ClassTypeAndroid.VIEWMODEL -> {
                    // todo generate correct tests for viewModel
//                    classDefinition.dependencies.forEach { dep ->
//                        val s = classesDictionary.filter { it.key == dep.sourceModuleId }
//                        if (s.isNotEmpty()) {
//                            val x = s.values.flatten().first { it.type == ClassTypeAndroid.REPOSITORY }
//                            if (x != null) {
//                                val repository = x.className
//                                appendLine("import com.awesomeapp.${NameMappings.modulePackageName(dep.sourceModuleId)}.$repository")
//                            }
//                        }
//                    }
             //   }

                ClassTypeAndroid.REPOSITORY -> {
                    classDefinition.dependencies.forEach { dep ->
                        val s = classesDictionary.filter { it.key == dep.sourceModuleId }
                        if (s.isNotEmpty()) {
                            val x = s.values.flatten().first { it.type == ClassTypeAndroid.API }
                            if (x != null) {
                                val repository = x.className
                                appendLine("import com.awesomeapp.${NameMappings.modulePackageName(dep.sourceModuleId)}.$repository")
                            }
                        }
                    }
                }

                else -> {}
            }
        }

        val classAnnotations = "@OptIn(ExperimentalCoroutinesApi::class)"
        val runnerAnnotations = if (classDefinition.type == ClassTypeAndroid.REPOSITORY ||
            classDefinition.type == ClassTypeAndroid.USECASE) {
            "\n@RunWith(RobolectricTestRunner::class)\n@Config(sdk = [28])"
        } else {
            ""
        }

        return """
            |package com.awesomeapp.${NameMappings.modulePackageName(moduleDefinition.moduleId)}
            |
            |$imports
            |
            |$classAnnotations$runnerAnnotations
            |class ${className}Test {
            |    $testContent
            |}
        """.trimMargin()
    }

    private fun generateRepositoryTest(
        moduleDefinition: ModuleClassDefinitionAndroid,
        classDefinition: ClassDefinitionAndroid,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val moduleId = NameMappings.moduleName(moduleDefinition.moduleId)
        val databaseClass = a[moduleId]?.firstOrNull { it.type == ClassTypeAndroid.DATABASE }?.className
            ?: "Database${moduleDefinition.moduleNumber}_1"
        val daoClass = a[moduleId]?.firstOrNull { it.type == ClassTypeAndroid.DAO }?.className
            ?: "Dao${moduleDefinition.moduleNumber}_1"
        val entityClass = a[moduleId]?.firstOrNull { it.type == ClassTypeAndroid.ENTITY }?.className
            ?: "Entity${moduleDefinition.moduleNumber}_1"
        val repositoryClass = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}"

        val testContent = """
            |    private lateinit var db: $databaseClass
            |    private lateinit var dao: $daoClass
            |    private lateinit var repository: $repositoryClass
            |
            |    @Before
            |    fun setup() {
            |        val context = ApplicationProvider.getApplicationContext<Context>()
            |        db = Room.inMemoryDatabaseBuilder(context, $databaseClass::class.java)
            |            .allowMainThreadQueries()
            |            .build()
            |        dao = db.dao()
            |        repository = $repositoryClass(dao)
            |    }
            |
            |    @After
            |    fun tearDown() {
            |        db.close()
            |    }
            |
            |    @Test
            |    fun `observeItems emits items`() = runTest {
            |        dao.upsertAll(listOf($entityClass(id = 1, title = "Hello", updatedAt = 1L)))
            |        val items = repository.observeItems().first()
            |        assertTrue(items.isNotEmpty())
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

    private fun generateEntityTest(className: String): String = """
        |    @Test
        |    fun `entity can be created`() {
        |        val entity = $className(id = 1, title = "Title", updatedAt = 1L)
        |        assertNotNull(entity)
        |    }
    """.trimMargin()

    private fun generateDaoTest(className: String): String = """
        |    @Test
        |    fun `dao interface exists`() {
        |        assertTrue(true)
        |    }
    """.trimMargin()

    private fun generateDatabaseTest(className: String): String = """
        |    @Test
        |    fun `database class exists`() {
        |        assertTrue(true)
        |    }
    """.trimMargin()

    private fun generateScreenTest(className: String): String = """
        |    @Test
        |    fun `screen composable exists`() {
        |        assertTrue(true)
        |    }
    """.trimMargin()

    private fun generateViewModelTest(
        className: String,
        dependencies: List<ClassDependencyAndroid>,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
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
                                        "com.awesomeapp.${NameMappings.modulePackageName(innerDep.sourceModuleId)}.${innerX.className}()"
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
        |    fun `default state is loading`() {
        |        val state = $className()
        |        assertTrue(state.isLoading)
        |    }
    """.trimMargin()

    private fun generateModelTest(className: String): String = """
        |    @Test
        |    fun `test model creation`() {
        |        val model = $className(id = 1, title = "Title")
        |        assertNotNull(model)
        |    }
    """.trimMargin()

    private fun generateUseCaseTest(
        moduleDefinition: ModuleClassDefinitionAndroid,
        className: String,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val moduleId = NameMappings.moduleName(moduleDefinition.moduleId)
        val databaseClass = a[moduleId]?.firstOrNull { it.type == ClassTypeAndroid.DATABASE }?.className
            ?: "Database${moduleDefinition.moduleNumber}_1"
        val daoClass = a[moduleId]?.firstOrNull { it.type == ClassTypeAndroid.DAO }?.className
            ?: "Dao${moduleDefinition.moduleNumber}_1"
        val entityClass = a[moduleId]?.firstOrNull { it.type == ClassTypeAndroid.ENTITY }?.className
            ?: "Entity${moduleDefinition.moduleNumber}_1"
        val repositoryClass = a[moduleId]?.firstOrNull { it.type == ClassTypeAndroid.REPOSITORY }?.className
            ?: "Repository${moduleDefinition.moduleNumber}_1"
        return """
            |    private lateinit var db: $databaseClass
            |    private lateinit var dao: $daoClass
            |    private lateinit var repository: $repositoryClass
            |    private lateinit var useCase: $className
            |
            |    @Before
            |    fun setup() {
            |        val context = ApplicationProvider.getApplicationContext<Context>()
            |        db = Room.inMemoryDatabaseBuilder(context, $databaseClass::class.java)
            |            .allowMainThreadQueries()
            |            .build()
            |        dao = db.dao()
            |        repository = $repositoryClass(dao)
            |        useCase = $className(repository)
            |    }
            |
            |    @After
            |    fun tearDown() {
            |        db.close()
            |    }
            |
            |    @Test
            |    fun `invoke returns items`() = runTest {
            |        dao.upsertAll(listOf($entityClass(id = 1, title = "Hi", updatedAt = 1L)))
            |        val items = useCase().first()
            |        assertTrue(items.isNotEmpty())
            |    }
        """.trimMargin()
    }
}
