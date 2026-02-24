package io.github.cdsap.projectgenerator.generator.planner

import io.github.cdsap.projectgenerator.model.ClassTypeAndroid
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ModuleClassPlannerAndroidLegacyTest {

    private val planner = ModuleClassPlannerAndroidLegacy()

    @Test
    fun `viewmodel dependency stays in same module when repository exists`() {
        val module = projectGraph(id = "module_0_20", classes = 12, nodes = listOf(projectGraph(id = "module_0_4", classes = 12)))

        val definition = planner.planModuleClasses(module)
        val viewModel = definition.classes.first { it.type == ClassTypeAndroid.VIEWMODEL }

        assertEquals(1, viewModel.dependencies.size)
        assertEquals("module_0_20", viewModel.dependencies.first().sourceModuleId)
    }

    @Test
    fun `viewmodel has no dependency when repository is not generated locally`() {
        val module = projectGraph(id = "module_0_21", classes = 12, nodes = listOf(projectGraph(id = "module_0_4", classes = 12)))

        val definition = planner.planModuleClasses(module)
        val viewModel = definition.classes.first { it.type == ClassTypeAndroid.VIEWMODEL }

        assertTrue(viewModel.dependencies.isEmpty())
    }

    private fun projectGraph(
        id: String,
        classes: Int,
        nodes: List<ProjectGraph> = emptyList()
    ) = ProjectGraph(
        id = id,
        layer = 0,
        nodes = nodes,
        type = TypeProject.ANDROID_LIB,
        classes = classes
    )
}
