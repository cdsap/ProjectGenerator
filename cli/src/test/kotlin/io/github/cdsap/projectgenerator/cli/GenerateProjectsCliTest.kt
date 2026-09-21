package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.parse
import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.ClassesPerModuleType
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Language
import io.github.cdsap.projectgenerator.model.Shape
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.VersionsFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GenerateProjectsCliTest {

    @Test
    fun `room database flag is rejected for jvm type`() {
        val error = assertThrows<UsageError> {
            GenerateProjects().parse(
                listOf(
                    "--modules", "6",
                    "--type", "jvm",
                    "--room-database"
                )
            )
        }
        assertTrue(error.message?.contains("--room-database is only available when --type android.") == true)
    }

    @Test
    fun `android kotlin multiplatform library flag is rejected for jvm type`() {
        val error = assertThrows<UsageError> {
            GenerateProjects().parse(
                listOf(
                    "--modules", "6",
                    "--type", "jvm",
                    "--android-kotlin-multiplatform-library"
                )
            )
        }
        assertTrue(
            error.message?.contains("--android-kotlin-multiplatform-library is only available when --type android.") == true
        )
    }

    @Test
    fun `resolve rejects room database for jvm type`() {
        val error = assertThrows<GenerateProjectRequestValidationException> {
            GenerateProjectRequest.resolve(
                options(
                    typeOfProjectRequested = TypeProjectRequested.JVM,
                    versionsOverrides = VersionsOverrides(
                        dependencyInjection = DependencyInjection.HILT,
                        develocityUrl = null,
                        roomDatabase = true,
                        kotlinMultiplatformLibrary = false
                    )
                )
            )
        }
        assertTrue(error.message?.contains("--room-database is only available when --type android.") == true)
    }

    @Test
    fun `resolve rejects android kotlin multiplatform library for jvm type`() {
        val error = assertThrows<GenerateProjectRequestValidationException> {
            GenerateProjectRequest.resolve(
                options(
                    typeOfProjectRequested = TypeProjectRequested.JVM,
                    versionsOverrides = VersionsOverrides(
                        dependencyInjection = DependencyInjection.HILT,
                        develocityUrl = null,
                        roomDatabase = false,
                        kotlinMultiplatformLibrary = true
                    )
                )
            )
        }
        assertTrue(
            error.message?.contains("--android-kotlin-multiplatform-library is only available when --type android.") == true
        )
    }

    @Test
    fun `classes module lower than minimum is rejected`() {
        val error = assertThrows<UsageError> {
            GenerateProjects().parse(
                listOf(
                    "--modules", "6",
                    "--classes-module", "9"
                )
            )
        }
        assertTrue(error.message?.contains("classes per module must be >= 10") == true)
    }

    @Test
    fun `unsupported gradle version lists supported values`() {
        val error = assertThrows<UsageError> {
            GenerateProjects().parse(
                listOf(
                    "--modules", "6",
                    "--gradle", "9.9.9"
                )
            )
        }

        assertTrue(error.message?.contains("Unknown Gradle version: 9.9.9") == true)
        assertTrue(error.message?.contains(Gradle.supportedDisplayValues()) == true)
    }

    @Test
    fun `default output path for kts nests project name and project_kts`() {
        val resolved = ProjectOutputPathResolver.defaultRootPath(null, Language.KTS, "sample")

        assertEquals("projects_generated/sample/project_kts", resolved)
    }

    @Test
    fun `default output path for groovy nests project name and project_groovy`() {
        val resolved = ProjectOutputPathResolver.defaultRootPath(null, Language.GROOVY, "sample")

        assertEquals("projects_generated/sample/project_groovy", resolved)
    }

    @Test
    fun `default output path for both languages nests project name only`() {
        val resolved = ProjectOutputPathResolver.defaultRootPath(null, Language.BOTH, "sample")

        assertEquals("projects_generated/sample", resolved)
    }

    @Test
    fun `output dir is used directly for kts`() {
        val resolved = ProjectOutputPathResolver.defaultRootPath(".", Language.KTS, "sample")

        assertEquals(".", resolved)
    }

    @Test
    fun `output dir is used directly for groovy`() {
        val resolved = ProjectOutputPathResolver.defaultRootPath("/tmp/out", Language.GROOVY, "sample")

        assertEquals("/tmp/out", resolved)
    }

    @Test
    fun `output dir is used directly for both languages`() {
        val resolved = ProjectOutputPathResolver.defaultRootPath("/tmp/out", Language.BOTH, "sample")

        assertEquals("/tmp/out", resolved)
    }

    @Test
    fun `develocity url enables develocity when develocity flag is absent`() {
        val request = GenerateProjectRequest.resolve(
            options(
                typeOfProjectRequested = TypeProjectRequested.ANDROID,
                projectName = "named",
                versionsOverrides = VersionsOverrides(
                    dependencyInjection = DependencyInjection.HILT,
                    develocityUrl = "https://develocity.example",
                    roomDatabase = false,
                    kotlinMultiplatformLibrary = false
                )
            )
        )

        assertTrue(request.develocity)
        assertEquals("https://develocity.example", request.versions.project.develocityUrl)
    }

    @Test
    fun `develocity stays disabled when flag and url are both absent`() {
        assertFalse(resolveDevelocityEnabled(develocity = false, develocityUrl = null))
    }

    @Test
    fun `resolve builds default project name and nested root path`() {
        val request = GenerateProjectRequest.resolve(
            options(
                modules = 12,
                shape = Shape.TRIANGLE,
                typeOfProjectRequested = TypeProjectRequested.JVM,
                projectName = null
            )
        )

        assertEquals("jvmTriangle12modules", request.projectName)
        assertEquals("projects_generated/jvmTriangle12modules/project_kts", request.projectRootPath)
    }

    @Test
    fun `resolve nests groovy default root path under project_groovy`() {
        val request = resolveRequest(language = Language.GROOVY, outputDir = null, projectName = "sample")

        assertEquals("projects_generated/sample/project_groovy", request.projectRootPath)
    }

    @Test
    fun `resolve nests both-languages default root path under project name only`() {
        val request = resolveRequest(language = Language.BOTH, outputDir = null, projectName = "sample")

        assertEquals("projects_generated/sample", request.projectRootPath)
    }

    @Test
    fun `resolve uses explicit output dir for any language`() {
        val request = resolveRequest(language = Language.BOTH, outputDir = "/tmp/out", projectName = "sample")

        assertEquals("/tmp/out", request.projectRootPath)
    }

    private fun resolveRequest(
        language: Language,
        outputDir: String?,
        projectName: String?
    ): GenerateProjectRequest {
        return GenerateProjectRequest.resolve(
            options(language = language, outputDir = outputDir, projectName = projectName)
        )
    }

    private fun options(
        modules: Int = 6,
        shape: Shape = Shape.RECTANGLE,
        language: Language = Language.KTS,
        typeOfProjectRequested: TypeProjectRequested = TypeProjectRequested.JVM,
        classesPerModule: ClassesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10),
        typeOfStringResources: TypeOfStringResources = TypeOfStringResources.NORMAL,
        layers: Int = 5,
        generateUnitTest: Boolean = false,
        cliGradle: String? = null,
        develocityFlag: Boolean = false,
        versionsFile: VersionsFile? = null,
        outputDir: String? = null,
        projectName: String? = null,
        versionsOverrides: VersionsOverrides = VersionsOverrides(
            dependencyInjection = DependencyInjection.HILT,
            develocityUrl = null,
            roomDatabase = false,
            kotlinMultiplatformLibrary = false
        )
    ): GenerateProjectOptions {
        return GenerateProjectOptions(
            modules = modules,
            shape = shape,
            language = language,
            typeOfProjectRequested = typeOfProjectRequested,
            classesPerModule = classesPerModule,
            typeOfStringResources = typeOfStringResources,
            layers = layers,
            generateUnitTest = generateUnitTest,
            cliGradle = cliGradle,
            develocityFlag = develocityFlag,
            versionsFile = versionsFile,
            outputDir = outputDir,
            projectName = projectName,
            versionsOverrides = versionsOverrides
        )
    }
}
