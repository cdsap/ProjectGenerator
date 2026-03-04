package io.github.cdsap.projectgenerator.generator.planner

import io.github.cdsap.projectgenerator.model.ClassTypeAndroid
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ModuleClassPlannerAndroidTest {

    @Test
    fun `app module with low class count still includes activity and baseline classes`() {
        val planner = ModuleClassPlannerAndroid()
        val module = ProjectGraph(
            id = "module_2_9",
            layer = 2,
            nodes = emptyList(),
            type = TypeProject.ANDROID_APP,
            classes = 5
        )

        val result = planner.planModuleClasses(module)
        val types = result.classes.map { it.type }

        assertTrue(types.contains(ClassTypeAndroid.ACTIVITY))
        assertTrue(types.contains(ClassTypeAndroid.SCREEN))
        assertTrue(types.contains(ClassTypeAndroid.VIEWMODEL))
        assertEquals(10, result.classes.size)
    }
}
