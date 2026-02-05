package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.Android
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
import io.github.cdsap.projectgenerator.NameMappings
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.io.File
import java.nio.file.Path

class ProjectGeneratorE2EAgp9Test {
    @TempDir
    lateinit var tempDir: Path

    @ParameterizedTest
    @EnumSource(Shape::class)
    fun testBuildAndroidProjectsWithGradle9(shape: Shape) {
        val modules = 50
        ProjectGenerator(
            modules,
            shape,
            Language.KTS,
            TypeProjectRequested.ANDROID,
            ClassesPerModule(ClassesPerModuleType.FIXED, 20),
            Versions(project = Project(jdk = "17"), android = Android(agp = "9.0.0")),
            TypeOfStringResources.LARGE,
            5,
            true,
            GradleWrapper(Gradle.GRADLE_9_3_0),
            path = tempDir.toFile().path,
            false,
            projectName = "${shape.name.lowercase().capitalize()}$modules"

        ).write()
        val filePath = File("$tempDir/${shape.name.lowercase().capitalize()}${modules}/project_kts")
        val result = GradleRunner.create()
            .withProjectDir(filePath)
            .withArguments("assembleDebug")
            .build();
        assert(result.output.contains("BUILD SUCCESSFUL"))
        val resultTest = GradleRunner.create()
            .withProjectDir(filePath)
            .withArguments("testDebugUnitTest")
            .build()

        val layerDir = NameMappings.layerName(0)
        val moduleDir = NameMappings.moduleName("module_0_1")
        assert(
            File(
                "$tempDir/${
                    shape.name.lowercase().capitalize()
                }${modules}/project_kts/$layerDir/$moduleDir/build"
            ).exists()
                && File(
                "$tempDir/${
                    shape.name.lowercase().capitalize()
                }${modules}/project_kts/$layerDir/$moduleDir/build"
            ).isDirectory
        )

        assert(resultTest.output.contains("BUILD SUCCESSFUL"))
    }

}
