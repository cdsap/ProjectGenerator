package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.DefaultTestVersions.Companion.LATEST_GRADLE
import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.ClassesPerModuleType
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Language
import io.github.cdsap.projectgenerator.model.Project
import io.github.cdsap.projectgenerator.model.Shape
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.io.File
import java.nio.file.Path

class Agp9NewDslDiVariantsE2EValidationTest {
    @TempDir
    lateinit var tempDir: Path

    @ParameterizedTest
    @EnumSource(DependencyInjection::class)
    fun `agp9 android project without newDsl opt-out compiles and reuses configuration cache`(
        di: DependencyInjection
    ) {
        val projectName = "agp9_newdsl_${di.name.lowercase()}"
        ProjectGenerator(
            modules = 6,
            shape = Shape.FLAT,
            language = Language.KTS,
            typeOfProjectRequested = TypeProjectRequested.ANDROID,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10),
            versions = Versions(
                project = Project(jdk = "21"),
                di = di
            ),
            typeOfStringResources = TypeOfStringResources.NORMAL,
            layers = 2,
            generateUnitTest = false,
            gradle = GradleWrapper(LATEST_GRADLE),
            projectRootPath = "${tempDir.toFile().path}/$projectName/project_kts",
            projectName = projectName
        ).write()

        val projectDir = File("$tempDir/$projectName/project_kts")
        AndroidSdkTestSupport.writeLocalProperties(projectDir)

        val gradleProperties = File(projectDir, "gradle.properties").readText()
        assertFalse(
            gradleProperties.contains("android.newDsl"),
            "Generated gradle.properties must not opt out of AGP 9 new DSL for $di"
        )

        val first = runWithConfigurationCache(projectDir)
        assertTrue(first.output.contains("BUILD SUCCESSFUL"))
        assertTrue(
            first.output.contains("Configuration cache entry stored") ||
                first.output.contains("Calculating task graph"),
            "Expected configuration cache to store an entry for $di:\n${first.output}"
        )

        val second = runWithConfigurationCache(projectDir)
        assertTrue(second.output.contains("BUILD SUCCESSFUL"))
        assertTrue(
            second.output.contains("Reusing configuration cache") ||
                second.output.contains("Configuration cache entry reused"),
            "Expected configuration cache reuse for $di:\n${second.output}"
        )
    }

    private fun runWithConfigurationCache(projectDir: File) = GradleRunner.create()
        .withProjectDir(projectDir)
        .withArguments("--configuration-cache", "assembleDebug")
        .build()
}
