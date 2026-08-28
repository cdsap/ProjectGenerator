package io.github.cdsap.projectgenerator.generator

import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.model.ModuleClassDefinitionAndroid
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class GeneratedModuleLayoutTest {

    @Test
    fun `android app uses standard main test resource and manifest paths`() {
        val node = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_APP, 1)
        val layout = GeneratedModuleLayout.of("projects_generated/demo", node, kotlinMultiplatformLibrary = false)

        assertEquals(expectedPackage("projects_generated/demo", node, "src/main/kotlin"), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("projects_generated/demo", node, "src/test/kotlin"), layout.testKotlinPackageDir())
        assertEquals(expectedModuleFile("projects_generated/demo", node, "src/main/res/layout"), layout.resourcesLayoutDir())
        assertEquals(expectedModuleFile("projects_generated/demo", node, "src/main/res/values"), layout.resourcesValuesDir())
        assertEquals(expectedModuleFile("projects_generated/demo", node, "src/main/"), layout.manifestDir())
    }

    @Test
    fun `android library without kmp uses standard source set paths`() {
        val node = ProjectGraph("module_2_1", 2, emptyList(), TypeProject.ANDROID_LIB, 1)
        val layout = GeneratedModuleLayout.of("out", node, kotlinMultiplatformLibrary = false)

        assertEquals(expectedPackage("out", node, "src/main/kotlin"), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("out", node, "src/test/kotlin"), layout.testKotlinPackageDir())
        assertEquals(expectedModuleFile("out", node, "src/main/res/layout"), layout.resourcesLayoutDir())
        assertEquals(expectedModuleFile("out", node, "src/main/res/values"), layout.resourcesValuesDir())
        assertEquals(expectedModuleFile("out", node, "src/main/"), layout.manifestDir())
    }

    @Test
    fun `android kmp library uses androidMain and androidHostTest paths`() {
        val node = ProjectGraph("module_3_1", 3, emptyList(), TypeProject.ANDROID_LIB, 1)
        val layout = GeneratedModuleLayout.of("out", node, kotlinMultiplatformLibrary = true)

        assertEquals(expectedPackage("out", node, "src/androidMain/kotlin"), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("out", node, "src/androidHostTest/kotlin"), layout.testKotlinPackageDir())
        assertEquals(expectedModuleFile("out", node, "src/androidMain/res/layout"), layout.resourcesLayoutDir())
        assertEquals(expectedModuleFile("out", node, "src/androidMain/res/values"), layout.resourcesValuesDir())
        assertEquals(expectedModuleFile("out", node, "src/androidMain/"), layout.manifestDir())
    }

    @Test
    fun `android app ignores kmp flag for source set paths`() {
        val node = ProjectGraph("module_1_2", 1, emptyList(), TypeProject.ANDROID_APP, 1)
        val layout = GeneratedModuleLayout.of("out", node, kotlinMultiplatformLibrary = true)

        assertEquals(expectedPackage("out", node, "src/main/kotlin"), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("out", node, "src/test/kotlin"), layout.testKotlinPackageDir())
        assertEquals(expectedModuleFile("out", node, "src/main/res/layout"), layout.resourcesLayoutDir())
        assertEquals(expectedModuleFile("out", node, "src/main/"), layout.manifestDir())
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

        val appLayout = GeneratedModuleLayout.of("out", withType, kotlinMultiplatformLibrary = true)
        val libLayout = GeneratedModuleLayout.of("out", withoutType, kotlinMultiplatformLibrary = true)

        assertEquals(
            File("out/${NameMappings.layerName(4)}/module_4_1/src/main/kotlin/com/awesomeapp/module_4_1/"),
            appLayout.mainKotlinPackageDir()
        )
        assertEquals(
            File("out/${NameMappings.layerName(5)}/module_5_1/src/androidMain/kotlin/com/awesomeapp/module_5_1/"),
            libLayout.mainKotlinPackageDir()
        )
        assertEquals(
            File("out/${NameMappings.layerName(5)}/module_5_1/src/androidHostTest/kotlin/com/awesomeapp/module_5_1/"),
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
            testKotlinSourceDir = "src/customTest/kotlin"
        )

        assertEquals(expectedPackage("out", node, "src/customMain/kotlin"), layout.mainKotlinPackageDir())
        assertEquals(expectedPackage("out", node, "src/customTest/kotlin"), layout.testKotlinPackageDir())
    }

    private fun expectedPackage(projectName: String, node: ProjectGraph, sourceDir: String): File {
        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        val packageDir = NameMappings.modulePackageName(node.id)
        return File("$projectName/$layerDir/$moduleDir/$sourceDir/com/awesomeapp/$packageDir/")
    }

    private fun expectedModuleFile(projectName: String, node: ProjectGraph, relativePath: String): File {
        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        return File("$projectName/$layerDir/$moduleDir/$relativePath")
    }
}
