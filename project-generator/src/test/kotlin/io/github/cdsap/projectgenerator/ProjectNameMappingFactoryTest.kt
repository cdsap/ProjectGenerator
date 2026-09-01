package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProjectNameMappingFactoryTest {

    @Test
    fun `maps app layer to app and honors custom and fallback layer names`() {
        val maps = ProjectNameMappingFactory.create(
            layers = 3,
            nodes = emptyList(),
            layerNames = listOf("core", "feature"),
            moduleNameParts = listOf("push", "contact")
        )

        assertEquals("core", maps.layerNames[0])
        assertEquals("feature", maps.layerNames[1])
        assertEquals("layer_2", maps.layerNames[2])
        assertEquals("app", maps.layerNames[3])
    }

    @Test
    fun `generates module names from module name parts in sequence`() {
        val nodes = listOf(
            node("module_0_1", layer = 0),
            node("module_0_2", layer = 0),
            node("module_1_3", layer = 1),
            node("module_2_4", layer = 2)
        )
        val parts = listOf("push", "contact", "login")

        val maps = ProjectNameMappingFactory.create(
            layers = 2,
            nodes = nodes,
            layerNames = listOf("core", "feature"),
            moduleNameParts = parts
        )

        assertEquals("push", maps.moduleNames["module_0_1"])
        assertEquals("contact", maps.moduleNames["module_0_2"])
        assertEquals("login", maps.moduleNames["module_1_3"])
        assertEquals("app", maps.moduleNames["module_2_4"])
    }

    @Test
    fun `module names wrap with hyphenated parts beyond the alphabet size`() {
        val nodes = (1..4).map { index ->
            node("module_0_$index", layer = 0)
        }
        val parts = listOf("alpha", "beta")

        val maps = ProjectNameMappingFactory.create(
            layers = 1,
            nodes = nodes,
            layerNames = listOf("core"),
            moduleNameParts = parts
        )

        assertEquals("alpha", maps.moduleNames["module_0_1"])
        assertEquals("beta", maps.moduleNames["module_0_2"])
        assertEquals("alpha-beta", maps.moduleNames["module_0_3"])
        assertEquals("beta-beta", maps.moduleNames["module_0_4"])
    }

    @Test
    fun `configure applies computed maps to NameMappings`() {
        val previousLayers = NameMappings.layerNames
        val previousModules = NameMappings.moduleNames
        try {
            val maps = ProjectNameMappingFactory.create(
                layers = 1,
                nodes = listOf(node("module_0_1", layer = 0), node("module_1_2", layer = 1)),
                layerNames = listOf("domain"),
                moduleNameParts = listOf("push")
            )
            NameMappings.configure(maps)

            assertEquals("domain", NameMappings.layerName(0))
            assertEquals("app", NameMappings.layerName(1))
            assertEquals("push", NameMappings.moduleName("module_0_1"))
            assertEquals("app", NameMappings.moduleName("module_1_2"))
        } finally {
            NameMappings.layerNames = previousLayers
            NameMappings.moduleNames = previousModules
        }
    }

    private fun node(id: String, layer: Int) = ProjectGraph(
        id = id,
        layer = layer,
        nodes = emptyList(),
        type = TypeProject.ANDROID_LIB,
        classes = 1
    )
}
