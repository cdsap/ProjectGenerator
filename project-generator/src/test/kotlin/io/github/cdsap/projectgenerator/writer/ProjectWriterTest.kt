package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import io.github.cdsap.projectgenerator.writer.ProjectWriter
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
            true
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
}
