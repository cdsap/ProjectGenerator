package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.DefaultTestVersions.Companion.LATEST_GRADLE
import io.github.cdsap.projectgenerator.DefaultTestVersions.Companion.OLDEST_SUPPORTED_GRADLE
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import io.github.cdsap.projectgenerator.writer.ProjectWriter
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class ProjectWriterTest {

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun testWriteProject() {
        val nodes = listOf(
            ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_APP, 10),
            ProjectGraph("module_1_2", 1, emptyList(), TypeProject.ANDROID_LIB, 10),
            ProjectGraph(
                "module_2_1", 2, emptyList(), TypeProject.LIB, 10
            )
        )
        val languages = listOf(
            LanguageAttributes("gradle", "${tempDir}/project_groovy"),
            LanguageAttributes("gradle.kts", "${tempDir}/project_kts")
        )

        val versions = Versions()
        val typeOfProjectRequested = TypeProjectRequested.ANDROID

        val projectWriter = ProjectWriter(
            nodes,
            languages,
            versions,
            typeOfProjectRequested,
            TypeOfStringResources.NORMAL,
            false,
            GradleWrapper(OLDEST_SUPPORTED_GRADLE),
            true,
            "awesomeapp"
        )

        projectWriter.write()
        languages.forEach {
            assertDirectoryExists(File(it.projectName))
            assertFileExists(File("${it.projectName}/build.${it.extension}"))
            assertFileExists(File("${it.projectName}/settings.${it.extension}"))
            assertFileExists(File("${it.projectName}/gradle.properties"))
        }

    }

    private fun assertDirectoryExists(directory: File) {
        assert(directory.exists() && directory.isDirectory) {
            "Directory does not exist or is not a directory: $directory"
        }
    }

    private fun assertFileExists(file: File) {
        assert(file.exists() && file.isFile) {
            "File does not exist or is not a regular file: $file"
        }
    }

    @Test
    fun `generates manual room wiring for DI none`() {
        val node = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_APP, 12)
        val language = LanguageAttributes("gradle.kts", "${tempDir}/project_kts")
        val versions = Versions(
            di = DependencyInjection.NONE,
            android = Android(roomDatabase = true)
        )

        val projectWriter = ProjectWriter(
            listOf(node),
            listOf(language),
            versions,
            TypeProjectRequested.ANDROID,
            TypeOfStringResources.NORMAL,
            false,
            GradleWrapper(LATEST_GRADLE),
            false,
            "manual_room_none"
        )

        projectWriter.write()

        val activityFile = File(
            "${language.projectName}/${NameMappings.layerName(1)}/${NameMappings.moduleName("module_1_1")}" +
                "/src/main/kotlin/com/awesomeapp/${NameMappings.modulePackageName("module_1_1")}/Activity1_10.kt"
        )

        assertTrue(activityFile.exists(), "Expected generated Activity file to exist")
        val content = activityFile.readText()
        assertTrue(content.contains("ViewModelProvider.Factory"))
        assertTrue(content.contains("Room.databaseBuilder"))
        assertTrue(content.contains("by viewModels { viewModelFactory }"))
    }

    @Test
    fun `uses android kotlin multiplatform library root plugin alias when enabled`() {
        val node = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_LIB, 10)
        val language = LanguageAttributes("gradle.kts", "${tempDir}/project_kts_kmp")
        val versions = Versions(android = Android(kotlinMultiplatformLibrary = true))
        val projectWriter = ProjectWriter(
            listOf(node),
            listOf(language),
            versions,
            TypeProjectRequested.ANDROID,
            TypeOfStringResources.NORMAL,
            false,
            GradleWrapper(LATEST_GRADLE),
            false,
            "kmp_android_lib_alias"
        )

        projectWriter.write()

        val rootBuild = File("${language.projectName}/build.gradle.kts")
        assertTrue(rootBuild.exists(), "Expected root build.gradle.kts to exist")
        val content = rootBuild.readText()
        assertTrue(content.contains("alias(libs.plugins.android.kotlin.multiplatform.library) apply false"))
    }

    @Test
    fun `does not generate legacy hilt modules for non-room mode`() {
        val node = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_LIB, 12)
        val language = LanguageAttributes("gradle.kts", "${tempDir}/project_kts_hilt_lean")
        val versions = Versions(di = DependencyInjection.HILT, android = Android(roomDatabase = false))
        val projectWriter = ProjectWriter(
            listOf(node),
            listOf(language),
            versions,
            TypeProjectRequested.ANDROID,
            TypeOfStringResources.NORMAL,
            false,
            GradleWrapper(LATEST_GRADLE),
            false,
            "hilt_lean_graph"
        )

        projectWriter.write()

        val moduleFile = File(
            "${language.projectName}/${NameMappings.layerName(1)}/${NameMappings.moduleName("module_1_1")}" +
                "/src/main/kotlin/com/awesomeapp/${NameMappings.modulePackageName("module_1_1")}/di/Module_1.kt"
        )
        assertTrue(!moduleFile.exists(), "Legacy Hilt module file should not be generated in non-room mode")
    }

    @Test
    fun `limits hilt android entry points to app launcher activity`() {
        val previousLayerNames = NameMappings.layerNames
        val previousModuleNames = NameMappings.moduleNames
        NameMappings.layerNames = mapOf(1 to "layer_1", 2 to "app")
        NameMappings.moduleNames = mapOf("module_1_1" to "sample-lib", "module_2_1" to "app")
        try {
            val nodes = listOf(
                ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_LIB, 12),
                ProjectGraph("module_2_1", 2, emptyList(), TypeProject.ANDROID_APP, 12)
            )
            val language = LanguageAttributes("gradle.kts", "${tempDir}/project_kts_hilt_entrypoints")
            val versions = Versions(di = DependencyInjection.HILT, android = Android(roomDatabase = false))
            val projectWriter = ProjectWriter(
                nodes,
                listOf(language),
                versions,
                TypeProjectRequested.ANDROID,
                TypeOfStringResources.NORMAL,
                false,
                GradleWrapper(LATEST_GRADLE),
                false,
                "hilt_entrypoints"
            )

            projectWriter.write()

            val libActivity = File(
                "${language.projectName}/${NameMappings.layerName(1)}/${NameMappings.moduleName("module_1_1")}" +
                    "/src/main/kotlin/com/awesomeapp/${NameMappings.modulePackageName("module_1_1")}/Activity1_2.kt"
            )
            val appActivity = File(
                "${language.projectName}/${NameMappings.layerName(2)}/${NameMappings.moduleName("module_2_1")}" +
                    "/src/main/kotlin/com/awesomeapp/${NameMappings.modulePackageName("module_2_1")}/Activity1_2.kt"
            )
            assertTrue(libActivity.exists(), "Expected lib activity to exist")
            assertTrue(appActivity.exists(), "Expected app activity to exist")
            assertTrue(!libActivity.readText().contains("@AndroidEntryPoint"))
            assertTrue(appActivity.readText().contains("@AndroidEntryPoint"))
        } finally {
            NameMappings.layerNames = previousLayerNames
            NameMappings.moduleNames = previousModuleNames
        }
    }

    @Test
    fun `does not include settings or build plugins when additionalSettingsPlugins and additionalBuildGradleRootPlugins are empty`() {
        val nodes = listOf(
            ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_APP, 10),
        )
        val language = LanguageAttributes("gradle.kts", "${tempDir}/project_no_plugins")
        val versions = Versions(
            additionalSettingsPlugins = emptyList(),
            additionalBuildGradleRootPlugins = emptyList()
        )
        val projectWriter = ProjectWriter(
            nodes,
            listOf(language),
            versions,
            TypeProjectRequested.ANDROID,
            TypeOfStringResources.NORMAL,
            false,
            GradleWrapper(LATEST_GRADLE),
            false,
            "no_plugins_project"
        )

        projectWriter.write()

        val settingsFile = File("${language.projectName}/settings.gradle.kts")
        assertTrue(settingsFile.exists(), "Expected settings.gradle.kts to exist")
        val settingsContent = settingsFile.readText()
        assertTrue(
            !settingsContent.contains("spotlight") && !settingsContent.contains("com.fueledbycaffeine.spotlight"),
            "settings.gradle.kts should not contain Spotlight plugin when additionalSettingsPlugins is empty, but contained: $settingsContent"
        )

        val buildFile = File("${language.projectName}/build.gradle.kts")
        assertTrue(buildFile.exists(), "Expected build.gradle.kts to exist")
        val buildContent = buildFile.readText()
        assertTrue(
            !buildContent.contains("dependency-analysis") && !buildContent.contains("com.autonomousapps.dependency-analysis"),
            "build.gradle.kts should not contain dependency-analysis plugin when additionalBuildGradleRootPlugins is empty, but contained: $buildContent"
        )
    }

    @Test
    fun `writes android library sources into androidMain when kotlin multiplatform library is enabled`() {
        val node = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.ANDROID_LIB, 10)
        val language = LanguageAttributes("gradle.kts", "${tempDir}/project_kts_android_main")
        val versions = Versions(android = Android(kotlinMultiplatformLibrary = true))
        val projectWriter = ProjectWriter(
            listOf(node),
            listOf(language),
            versions,
            TypeProjectRequested.ANDROID,
            TypeOfStringResources.NORMAL,
            true,
            GradleWrapper(LATEST_GRADLE),
            false,
            "kmp_android_main_layout"
        )

        projectWriter.write()

        val moduleBase = "${language.projectName}/${NameMappings.layerName(1)}/${NameMappings.moduleName("module_1_1")}"
        assertTrue(File("$moduleBase/src/androidMain/kotlin").exists())
        assertTrue(File("$moduleBase/src/androidMain/res").exists())
        assertTrue(File("$moduleBase/src/androidMain/AndroidManifest.xml").exists())
        assertTrue(File("$moduleBase/src/androidHostTest/kotlin").exists())
    }
}
