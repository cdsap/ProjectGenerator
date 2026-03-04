package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.DefaultTestVersions.Companion.LATEST_GRADLE
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
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.io.File
import java.nio.file.Path

class AndroidKotlinMultiplatformLibraryE2EValidationTest {
    @TempDir
    lateinit var tempDir: Path

    @ParameterizedTest
    @EnumSource(DependencyInjection::class)
    fun `android kotlin multiplatform library project assembles for all di modes`(di: DependencyInjection) {
        val projectName = "android_kmp_library_e2e_${di.name.lowercase()}"
        ProjectGenerator(
            modules = 6,
            shape = Shape.FLAT,
            language = Language.KTS,
            typeOfProjectRequested = TypeProjectRequested.ANDROID,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10),
            versions = Versions(
                project = Project(jdk = "17"),
                di = di,
                android = Android(kotlinMultiplatformLibrary = true)
            ),
            typeOfStringResources = TypeOfStringResources.NORMAL,
            layers = 2,
            generateUnitTest = false,
            gradle = GradleWrapper(LATEST_GRADLE),
            path = tempDir.toFile().path,
            projectName = projectName
        ).write()

        val assemble = assemble(projectName)

        assertTrue(assemble.output.contains("BUILD SUCCESSFUL"))
    }

    @ParameterizedTest
    @EnumSource(DependencyInjection::class)
    fun `android kotlin multiplatform library with room project assembles for all di modes`(di: DependencyInjection) {
        val projectName = "android_kmp_library_room_e2e_${di.name.lowercase()}"
        ProjectGenerator(
            modules = 6,
            shape = Shape.FLAT,
            language = Language.KTS,
            typeOfProjectRequested = TypeProjectRequested.ANDROID,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10),
            versions = Versions(
                project = Project(jdk = "17"),
                di = di,
                android = Android(
                    kotlinMultiplatformLibrary = true,
                    roomDatabase = true
                )
            ),
            typeOfStringResources = TypeOfStringResources.NORMAL,
            layers = 2,
            generateUnitTest = false,
            gradle = GradleWrapper(LATEST_GRADLE),
            path = tempDir.toFile().path,
            projectName = projectName
        ).write()

        val assemble = assemble(projectName)

        assertTrue(assemble.output.contains("BUILD SUCCESSFUL"))
    }

    private fun assemble(projectName: String) = GradleRunner.create()
        .withProjectDir(File("$tempDir/$projectName/project_kts"))
        .withArguments("assemble")
        .build()
}
