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
import io.github.cdsap.projectgenerator.NameMappings
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class SingleE2EValidationTest {

    @TempDir
    lateinit var tempDir: Path


    @Test
    fun sanityTest(){
        val modules = 50
        val shape = Shape.RHOMBUS
        ProjectGenerator(
            modules = 50,
            shape =shape,
            language = Language.KTS,
            typeOfProjectRequested = TypeProjectRequested.ANDROID,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 20),
            versions = Versions(project = Project(jdk = "17")),
            typeOfStringResources = TypeOfStringResources.LARGE,
            layers = 5,
            generateUnitTest = true,
            gradle = GradleWrapper(Gradle.GRADLE_9_1_0),
            path = tempDir.toFile().path,
            projectName = "${shape.name.lowercase().capitalize()}_$modules"
        ).write()
        val filePath = File("$tempDir/${shape.name.lowercase().capitalize()}_${modules}/project_kts")
        val result = GradleRunner.create()
            .withProjectDir(filePath)
            .withArguments("assembleDebug")
            .build()
        println(result.output)
        assert(result.output.contains("BUILD SUCCESSFUL"))
        val resultTest = GradleRunner.create()
            .withProjectDir(filePath)
            .withArguments("testDebugUnitTest")
            .build()

        val layerDir = NameMappings.layerName(0)
        val moduleDir = NameMappings.moduleName("module_0_1")
        assert(
            File("$tempDir/${shape.name.lowercase().capitalize()}_${modules}/project_kts/$layerDir/$moduleDir/build").exists()
                && File("$tempDir/${shape.name.lowercase().capitalize()}_${modules}/project_kts/$layerDir/$moduleDir/build").isDirectory
        )

        assert(resultTest.output.contains("BUILD SUCCESSFUL"))

    }
}
