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

class ProjectGeneratorE2ETest {
    @TempDir
    lateinit var tempDir: Path

    @ParameterizedTest
    @EnumSource(Shape::class)
    fun testBuildAndroidProjects(shape: Shape) {
        val modules = 50

        ProjectGenerator(
            modules,
            shape,
            Language.KTS,
            TypeProjectRequested.ANDROID,
            ClassesPerModule(ClassesPerModuleType.FIXED, 20),
            Versions(project = Project(jdk = "17")),
            TypeOfStringResources.LARGE,
            5,
            true,
            GradleWrapper(Gradle.GRADLE_8_14_3),
            path = tempDir.toFile().path
        ).write()
        val filePath = File("$tempDir/android${shape.name.lowercase().capitalize()}${modules}modules/project_kts")
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
            File("$tempDir/${shape.name.lowercase()}_$modules/project_kts/$layerDir/$moduleDir/build").exists()
                && File("$tempDir/${shape.name.lowercase()}_$modules/project_kts/$layerDir/$moduleDir/build").isDirectory
        )

        assert(resultTest.output.contains("BUILD SUCCESSFUL"))
    }

    @ParameterizedTest
    @EnumSource(Shape::class)
    fun testJvmBuildProjects(shape: Shape) {
        val modules = 50
        ProjectGenerator(
            modules,
            shape,
            Language.KTS,
            TypeProjectRequested.JVM,
            ClassesPerModule(ClassesPerModuleType.FIXED, 20),
            Versions(project = Project(jdk = "17")),
            TypeOfStringResources.LARGE,
            5,
            true,
            GradleWrapper(Gradle.GRADLE_8_13),
            path = tempDir.toFile().path
        ).write()
        val filePath = File("$tempDir/jvm${shape.name.lowercase().capitalize()}${modules}modules/project_kts")
        val result = GradleRunner.create()
            .withProjectDir(filePath)
            .withArguments("build")
            .build();

        val layerDirJvm = NameMappings.layerName(0)
        val moduleDirJvm = NameMappings.moduleName("module_0_1")
        assert(
            File("$tempDir/${shape.name.lowercase()}_$modules/project_kts/$layerDirJvm/$moduleDirJvm/build").exists()
                && File("$tempDir/${shape.name.lowercase()}_$modules/project_kts/$layerDirJvm/$moduleDirJvm/build").isDirectory
        )

        println(result.output)
        assert(result.output.contains("BUILD SUCCESSFUL"))
    }

    @Test
    fun changedVersionReflectsInTomlFile() {
        val modules = 30
        val shape = Shape.RHOMBUS
        ProjectGenerator(
            modules,
            shape,
            Language.KTS,
            TypeProjectRequested.ANDROID,
            ClassesPerModule(ClassesPerModuleType.FIXED, 20),
            Versions(project = Project(jdk = "17"), android = Android(agp = "9.9.9")),
            TypeOfStringResources.LARGE,
            5,
            true,
            GradleWrapper(Gradle.GRADLE_8_14_3),
            path = tempDir.toFile().path
        ).write()
        val toml = File("$tempDir/android${shape.name.lowercase().capitalize()}${modules}modules/project_kts/gradle/libs.versions.toml")
        assert(toml.exists())
        assert(toml.readText().contains("agp = \"9.9.9\""))
    }
}

