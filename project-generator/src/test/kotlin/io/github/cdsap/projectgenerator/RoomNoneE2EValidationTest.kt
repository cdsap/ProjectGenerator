package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.ClassesPerModuleType
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Language
import io.github.cdsap.projectgenerator.model.Project
import io.github.cdsap.projectgenerator.model.Shape
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class RoomNoneE2EValidationTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `room with di none builds and runs unit tests`() {
        ProjectGenerator(
            modules = 10,
            shape = Shape.RHOMBUS,
            language = Language.KTS,
            typeOfProjectRequested = TypeProjectRequested.ANDROID,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 12),
            versions = Versions(
                project = Project(jdk = "17"),
                di = DependencyInjection.NONE,
                android = Android(roomDatabase = true)
            ),
            typeOfStringResources = TypeOfStringResources.NORMAL,
            layers = 3,
            generateUnitTest = true,
            gradle = GradleWrapper(Gradle.GRADLE_9_3_0),
            path = tempDir.toFile().path,
            projectName = "room_none_e2e"
        ).write()

        val projectDir = File("$tempDir/room_none_e2e/project_kts")

        val assemble = GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments("assembleDebug")
            .build()
        assertTrue(assemble.output.contains("BUILD SUCCESSFUL"))

        val tests = GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments("testDebugUnitTest")
            .build()
        assertTrue(tests.output.contains("BUILD SUCCESSFUL"))
    }
}
