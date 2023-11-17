package io.github.cdsap.generator

import io.github.cdsap.generator.model.*
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

        val numberOfClassPerModule = 10
        val versions = Versions("1.0.0", "1.8")
        val typeOfProjectRequested = TypeProjectRequested.ANDROID

        val projectWriter = ProjectWriter(
            nodes,
            languages,
            ClassesPerModule(ClassesPerModuleType.FIXED, numberOfClassPerModule),
            versions,
            typeOfProjectRequested,
            typeOfStringResources
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
