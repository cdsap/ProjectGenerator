package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

class ModulesWriterTest {

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `setup plans each module once and reuses that plan for obtain and generate`() = runBlocking {
        val modules = listOf(
            ProjectGraph("module_1_1", 1, emptyList(), TypeProject.LIB, 1),
            ProjectGraph("module_1_2", 1, emptyList(), TypeProject.LIB, 1),
            ProjectGraph("module_2_1", 2, emptyList(), TypeProject.LIB, 1)
        )
        val planner = CountingPlanner()
        val classGenerator = RecordingClassGenerator()
        val languages = listOf(LanguageAttributes("gradle.kts", tempDir.resolve("project").toString()))

        TestModulesWrite(
            classGenerator = classGenerator,
            classPlanner = planner,
            testGenerator = NoOpTestGenerator(),
            generateUnitTest = false,
            buildFilesGenerator = NoOpBuildFilesGenerator(),
            nodes = modules,
            languages = languages
        ).write()

        assertEquals(modules.size, planner.planCalls.get())
        assertEquals(modules.size, classGenerator.obtainedPlans.size)
        assertEquals(modules.size, classGenerator.generatedPlans.size)
        classGenerator.obtainedPlans.forEach { obtained ->
            assertSame(
                obtained,
                classGenerator.generatedPlans.single { it.moduleId == obtained.moduleId },
                "obtainClassesGenerated and generate must receive the same planned definition"
            )
        }
    }

    private class ModulePlan(val moduleId: String)

    private class CountingPlanner : ModuleClassPlanner<ModulePlan> {
        val planCalls = AtomicInteger(0)

        override fun planModuleClasses(node: ProjectGraph): ModulePlan {
            planCalls.incrementAndGet()
            return ModulePlan(node.id)
        }
    }

    private class RecordingClassGenerator : ClassGenerator<ModulePlan, String> {
        val obtainedPlans = CopyOnWriteArrayList<ModulePlan>()
        val generatedPlans = CopyOnWriteArrayList<ModulePlan>()

        override fun obtainClassesGenerated(
            moduleDefinition: ModulePlan,
            classesDictionary: MutableMap<String, CopyOnWriteArrayList<String>>
        ): MutableMap<String, CopyOnWriteArrayList<String>> {
            obtainedPlans.add(moduleDefinition)
            return classesDictionary
        }

        override fun generate(
            moduleDefinition: ModulePlan,
            projectName: String,
            a: MutableMap<String, CopyOnWriteArrayList<String>>
        ) {
            generatedPlans.add(moduleDefinition)
        }
    }

    private class NoOpTestGenerator : TestGenerator<ModulePlan, String> {
        override fun generate(
            moduleDefinition: ModulePlan,
            projectName: String,
            classesDictionary: MutableMap<String, CopyOnWriteArrayList<String>>
        ) = Unit
    }

    private class NoOpBuildFilesGenerator : BuildFilesGenerator {
        override fun generateBuildFiles(
            node: ProjectGraph,
            lang: LanguageAttributes,
            generateUnitTests: Boolean
        ) = Unit
    }

    private class TestModulesWrite(
        classGenerator: ClassGenerator<ModulePlan, String>,
        classPlanner: ModuleClassPlanner<ModulePlan>,
        testGenerator: TestGenerator<ModulePlan, String>,
        generateUnitTest: Boolean,
        buildFilesGenerator: BuildFilesGenerator,
        nodes: List<ProjectGraph>,
        languages: List<LanguageAttributes>
    ) : ModulesWrite<ModulePlan, String>(
        classGenerator = classGenerator,
        classPlanner = classPlanner,
        testGenerator = testGenerator,
        resourceGeneratorA = null,
        generateUnitTest = generateUnitTest,
        buildFilesGenerator = buildFilesGenerator,
        resources = null,
        nodes = nodes,
        languages = languages
    )
}
