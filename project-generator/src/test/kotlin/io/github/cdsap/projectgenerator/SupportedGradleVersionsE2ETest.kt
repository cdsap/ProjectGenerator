package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.ClassesPerModuleType
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Language
import io.github.cdsap.projectgenerator.model.Project
import io.github.cdsap.projectgenerator.model.Shape
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.nio.file.Path

class SupportedGradleVersionsE2ETest {

    @TempDir
    lateinit var tempDir: Path

    @ParameterizedTest(name = "builds generated JVM project with Gradle {0}")
    @MethodSource("supportedGradleVersions")
    fun `all supported gradle versions can build generated JVM project`(gradle: Gradle) {
        val projectName = "gradle_${gradle.version.replace('.', '_')}"
        val projectPath = tempDir.resolve(projectName).resolve("project_kts").toFile()

        ProjectGenerator(
            modules = 6,
            shape = Shape.FLAT,
            language = Language.KTS,
            typeOfProjectRequested = TypeProjectRequested.JVM,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10),
            versions = Versions(project = Project(jdk = "17")),
            typeOfStringResources = TypeOfStringResources.NORMAL,
            layers = 2,
            generateUnitTest = false,
            gradle = GradleWrapper(gradle),
            projectRootPath = projectPath.absolutePath,
            develocity = false,
            projectName = projectName
        ).write()

        val result = GradleRunner.create()
            .withProjectDir(projectPath)
            .withArguments("build")
            .build()

        val sampleModuleBuildDir = File(
            projectPath,
            "${NameMappings.layerName(0)}/${NameMappings.moduleName("module_0_1")}/build"
        )

        assert(result.output.contains("BUILD SUCCESSFUL")) {
            "Expected build to succeed for Gradle ${gradle.version}"
        }
        assert(sampleModuleBuildDir.exists() && sampleModuleBuildDir.isDirectory) {
            "Expected sample module build directory to exist for Gradle ${gradle.version}"
        }
    }

    companion object {
        @JvmStatic
        fun supportedGradleVersions(): List<Gradle> = Gradle.supported()
    }
}
