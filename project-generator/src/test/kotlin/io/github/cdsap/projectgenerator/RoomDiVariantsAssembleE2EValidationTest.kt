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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class RoomDiVariantsAssembleE2EValidationTest {
    @TempDir
    lateinit var tempDir: Path

    @ParameterizedTest
    @EnumSource(DependencyInjection::class)
    fun `room builds debug and release with assemble for all di modes`(di: DependencyInjection) {
        val projectName = "room_assemble_${di.name.lowercase()}"
        ProjectGenerator(
            modules = 8,
            shape = Shape.FLAT,
            language = Language.KTS,
            typeOfProjectRequested = TypeProjectRequested.ANDROID,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 12),
            versions = Versions(
                project = Project(jdk = "17"),
                di = di,
                android = Android(roomDatabase = true)
            ),
            typeOfStringResources = TypeOfStringResources.NORMAL,
            layers = 2,
            generateUnitTest = false,
            gradle = GradleWrapper(Gradle.GRADLE_9_3_0),
            path = tempDir.toFile().path,
            projectName = projectName
        ).write()

        val projectDir = File("$tempDir/$projectName/project_kts")
        val assemble = GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments("clean", "assemble")
            .build()

        assertTrue(assemble.output.contains("BUILD SUCCESSFUL"))
        assertTrue(assemble.output.contains("assembleDebug"))
        assertTrue(assemble.output.contains("assembleRelease"))
    }
}
