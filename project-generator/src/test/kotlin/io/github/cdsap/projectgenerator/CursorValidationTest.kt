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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class CursorValidationTest {

    @TempDir
    lateinit var tempDir: Path


    @Test
    fun sanityTest(){
        val modules = 50
        val shape = Shape.RHOMBUS
        ProjectGenerator(
            50,
            shape,
            Language.KTS,
            TypeProjectRequested.ANDROID,
            ClassesPerModule(ClassesPerModuleType.FIXED, 20),
            Versions(project = Project(jdk = "17")),
            TypeOfStringResources.LARGE,
            5,
            true,
            GradleWrapper(Gradle.GRADLE_8_13),
            path = tempDir.toFile().path
        ).write()
        val filePath = File("$tempDir/${shape.name.lowercase()}_$modules/project_kts")
        val result = GradleRunner.create()
            .withProjectDir(filePath)
            .withArguments("assembleDebug")
            .build();
        assert(result.output.contains("BUILD SUCCESSFUL"))
        val resultTest = GradleRunner.create()
            .withProjectDir(filePath)
            .withArguments("testDebugUnitTest")
            .build()

        assert(
            File("$tempDir/${shape.name.lowercase()}_$modules/project_kts/layer_0/module_0_1/build").exists()
                && File("$tempDir/${shape.name.lowercase()}_$modules/project_kts/layer_0/module_0_1/build").isDirectory
        )

        assert(resultTest.output.contains("BUILD SUCCESSFUL"))

    }
}
