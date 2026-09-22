package io.github.cdsap.projectgenerator.generator

import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.ProjectNameMaps
import io.github.cdsap.projectgenerator.model.ModuleClassDefinitionAndroid
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.File

class GeneratedModuleLayoutTest {

    private val emptyNameMaps = ProjectNameMaps(emptyMap(), emptyMap())

    @Test
    fun `android app uses standard main test resource and manifest paths`() {
        val node = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_APP, 1)
        val layout = GeneratedModuleLayout.of(
            "projects_generated/demo",
            node,
            kotlinMultiplatformLibrary = false,
            nameMaps = emptyNameMaps
        )

        assertEquals(expectedPackage("projects_generated/demo", node, "src/main/kotlin", emptyNameMaps), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("projects_generated/demo", node, "src/test/kotlin", emptyNameMaps), layout.testKotlinPackageDir())
        assertEquals(expectedModuleFile("projects_generated/demo", node, "src/main/res/layout", emptyNameMaps), layout.resourcesLayoutDir())
        assertEquals(expectedModuleFile("projects_generated/demo", node, "src/main/res/values", emptyNameMaps), layout.resourcesValuesDir())
        assertEquals(expectedModuleFile("projects_generated/demo", node, "src/main/", emptyNameMaps), layout.manifestDir())
    }

    @Test
    fun `android library without kmp uses standard source set paths`() {
        val node = ProjectGraph("module_2_1", 2, emptyList(), TypeProject.ANDROID_LIB, 1)
        val layout = GeneratedModuleLayout.of("out", node, kotlinMultiplatformLibrary = false, nameMaps = emptyNameMaps)

        assertEquals(expectedPackage("out", node, "src/main/kotlin", emptyNameMaps), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("out", node, "src/test/kotlin", emptyNameMaps), layout.testKotlinPackageDir())
        assertEquals(expectedModuleFile("out", node, "src/main/res/layout", emptyNameMaps), layout.resourcesLayoutDir())
        assertEquals(expectedModuleFile("out", node, "src/main/res/values", emptyNameMaps), layout.resourcesValuesDir())
        assertEquals(expectedModuleFile("out", node, "src/main/", emptyNameMaps), layout.manifestDir())
    }

    @Test
    fun `android kmp library uses androidMain and androidHostTest paths`() {
        val node = ProjectGraph("module_3_1", 3, emptyList(), TypeProject.ANDROID_LIB, 1)
        val layout = GeneratedModuleLayout.of("out", node, kotlinMultiplatformLibrary = true, nameMaps = emptyNameMaps)

        assertEquals(expectedPackage("out", node, "src/androidMain/kotlin", emptyNameMaps), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("out", node, "src/androidHostTest/kotlin", emptyNameMaps), layout.testKotlinPackageDir())
        assertEquals(expectedModuleFile("out", node, "src/androidMain/res/layout", emptyNameMaps), layout.resourcesLayoutDir())
        assertEquals(expectedModuleFile("out", node, "src/androidMain/res/values", emptyNameMaps), layout.resourcesValuesDir())
        assertEquals(expectedModuleFile("out", node, "src/androidMain/", emptyNameMaps), layout.manifestDir())
    }

    @Test
    fun `android app ignores kmp flag for source set paths`() {
        val node = ProjectGraph("module_1_2", 1, emptyList(), TypeProject.ANDROID_APP, 1)
        val layout = GeneratedModuleLayout.of("out", node, kotlinMultiplatformLibrary = true, nameMaps = emptyNameMaps)

        assertEquals(expectedPackage("out", node, "src/main/kotlin", emptyNameMaps), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("out", node, "src/test/kotlin", emptyNameMaps), layout.testKotlinPackageDir())
        assertEquals(expectedModuleFile("out", node, "src/main/res/layout", emptyNameMaps), layout.resourcesLayoutDir())
        assertEquals(expectedModuleFile("out", node, "src/main/", emptyNameMaps), layout.manifestDir())
    }

    @Test
    fun `module definition factory uses project type with android lib default`() {
        val withType = ModuleClassDefinitionAndroid(
            moduleId = "module_4_1",
            layer = 4,
            moduleNumber = 4,
            classes = emptyList(),
            projectType = TypeProject.ANDROID_APP
        )
        val withoutType = ModuleClassDefinitionAndroid(
            moduleId = "module_5_1",
            layer = 5,
            moduleNumber = 5,
            classes = emptyList()
        )

        val appLayout = GeneratedModuleLayout.of("out", withType, kotlinMultiplatformLibrary = true, nameMaps = emptyNameMaps)
        val libLayout = GeneratedModuleLayout.of("out", withoutType, kotlinMultiplatformLibrary = true, nameMaps = emptyNameMaps)

        assertEquals(
            File("out/layer_4/module_4_1/src/main/kotlin/com/awesomeapp/module_4_1/"),
            appLayout.mainKotlinPackageDir()
        )
        assertEquals(
            File("out/layer_5/module_5_1/src/androidMain/kotlin/com/awesomeapp/module_5_1/"),
            libLayout.mainKotlinPackageDir()
        )
        assertEquals(
            File("out/layer_5/module_5_1/src/androidHostTest/kotlin/com/awesomeapp/module_5_1/"),
            libLayout.testKotlinPackageDir()
        )
    }

    @Test
    fun `writer overload preserves injected kotlin source directories`() {
        val node = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.LIB, 1)
        val layout = GeneratedModuleLayout.of(
            projectName = "out",
            node = node,
            mainKotlinSourceDir = "src/customMain/kotlin",
            testKotlinSourceDir = "src/customTest/kotlin",
            nameMaps = emptyNameMaps
        )

        assertEquals(expectedPackage("out", node, "src/customMain/kotlin", emptyNameMaps), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("out", node, "src/customTest/kotlin", emptyNameMaps), layout.testKotlinPackageDir())
    }

    @Test
    fun `builds paths from explicit ProjectNameMaps without configuring NameMappings`() {
        val previousLayers = NameMappings.layerNames
        val previousModules = NameMappings.moduleNames
        NameMappings.layerNames = emptyMap()
        NameMappings.moduleNames = emptyMap()
        try {
            val nameMaps = ProjectNameMaps(
                layerNames = mapOf(1 to "platform"),
                moduleNames = mapOf("module_1_1" to "sample-lib")
            )
            val node = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_LIB, 1)
            val layout = GeneratedModuleLayout.of(
                projectName = "out",
                node = node,
                kotlinMultiplatformLibrary = false,
                nameMaps = nameMaps
            )

            assertEquals(
                File("out/platform/sample-lib/src/main/kotlin/com/awesomeapp/samplelib/"),
                layout.mainKotlinPackageDir()
            )
            assertEquals(
                File("out/platform/sample-lib/src/test/kotlin/com/awesomeapp/samplelib/"),
                layout.testKotlinPackageDir()
            )
            assertNotEquals(NameMappings.layerName(1), "platform")
            assertNotEquals(NameMappings.moduleName("module_1_1"), "sample-lib")
        } finally {
            NameMappings.layerNames = previousLayers
            NameMappings.moduleNames = previousModules
        }
    }

    private fun expectedPackage(
        projectName: String,
        node: ProjectGraph,
        sourceDir: String,
        nameMaps: ProjectNameMaps
    ): File {
        val layerDir = nameMaps.layerNames[node.layer] ?: "layer_${node.layer}"
        val moduleDir = nameMaps.moduleNames[node.id] ?: node.id
        val packageDir = moduleDir.replace("-", "")
        return File("$projectName/$layerDir/$moduleDir/$sourceDir/com/awesomeapp/$packageDir/")
    }

    private fun expectedModuleFile(
        projectName: String,
        node: ProjectGraph,
        relativePath: String,
        nameMaps: ProjectNameMaps
    ): File {
        val layerDir = nameMaps.layerNames[node.layer] ?: "layer_${node.layer}"
        val moduleDir = nameMaps.moduleNames[node.id] ?: node.id
        return File("$projectName/$layerDir/$moduleDir/$relativePath")
    }
}
