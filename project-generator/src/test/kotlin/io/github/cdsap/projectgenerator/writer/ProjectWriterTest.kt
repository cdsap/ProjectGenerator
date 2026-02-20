package io.github.cdsap.projectgenerator

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
            GradleWrapper(Gradle.GRADLE_8_13),
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
            GradleWrapper(Gradle.GRADLE_9_3_0),
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
            GradleWrapper(Gradle.GRADLE_9_3_0),
            false,
            "kmp_android_lib_alias"
        )

        projectWriter.write()

        val rootBuild = File("${language.projectName}/build.gradle.kts")
        assertTrue(rootBuild.exists(), "Expected root build.gradle.kts to exist")
        val content = rootBuild.readText()
        assertTrue(content.contains("alias(libs.plugins.android.kotlin.multiplatform.library) apply false"))
    }
}
