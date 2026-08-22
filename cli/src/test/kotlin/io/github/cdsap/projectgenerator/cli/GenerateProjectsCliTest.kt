package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.parse
import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Language
import io.github.cdsap.projectgenerator.model.Project
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.model.VersionsFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GenerateProjectsCliTest {

    @Test
    fun `resolveVersions uses defaults when versions file is absent`() {
        val resolved = resolveVersions(
            fileVersions = null,
            dependencyInjection = DependencyInjection.HILT,
            develocityUrl = null,
            roomDatabase = false,
            kotlinMultiplatformLibrary = false
        )

        assertEquals(Versions(), resolved)
    }

    @Test
    fun `resolveVersions keeps versions-file values when cli overrides are absent`() {
        val fileVersions = VersionsFile(
            project = Project(develocityUrl = "https://develocity.example"),
            android = Android(roomDatabase = true, kotlinMultiplatformLibrary = true),
            di = DependencyInjection.METRO
        )

        val resolved = resolveVersions(
            fileVersions = fileVersions,
            dependencyInjection = DependencyInjection.HILT,
            develocityUrl = null,
            roomDatabase = false,
            kotlinMultiplatformLibrary = false
        )

        assertEquals("https://develocity.example", resolved.project.develocityUrl)
        assertTrue(resolved.android.roomDatabase)
        assertTrue(resolved.android.kotlinMultiplatformLibrary)
        assertEquals(DependencyInjection.HILT, resolved.di)
    }

    @Test
    fun `resolveVersions applies cli overrides for develocity room kmp and di`() {
        val fileVersions = VersionsFile(
            project = Project(develocityUrl = "https://from-file.example"),
            android = Android(roomDatabase = false, kotlinMultiplatformLibrary = false),
            di = DependencyInjection.HILT
        )

        val resolved = resolveVersions(
            fileVersions = fileVersions,
            dependencyInjection = DependencyInjection.NONE,
            develocityUrl = "https://from-cli.example",
            roomDatabase = true,
            kotlinMultiplatformLibrary = true
        )

        assertEquals("https://from-cli.example", resolved.project.develocityUrl)
        assertTrue(resolved.android.roomDatabase)
        assertTrue(resolved.android.kotlinMultiplatformLibrary)
        assertEquals(DependencyInjection.NONE, resolved.di)
    }

    @Test
    fun `resolveVersions does not clear file android flags when cli flags are false`() {
        val fileVersions = VersionsFile(
            android = Android(roomDatabase = true, kotlinMultiplatformLibrary = true)
        )

        val resolved = resolveVersions(
            fileVersions = fileVersions,
            dependencyInjection = DependencyInjection.METRO,
            develocityUrl = null,
            roomDatabase = false,
            kotlinMultiplatformLibrary = false
        )

        assertTrue(resolved.android.roomDatabase)
        assertTrue(resolved.android.kotlinMultiplatformLibrary)
        assertEquals("", resolved.project.develocityUrl)
        assertEquals(DependencyInjection.METRO, resolved.di)
    }

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
    fun `gradle from versions file is used when flag is absent`() {
        val configured = Gradle.supported()[1]
        val resolved = resolveGradle(null, VersionsFile(gradle = configured))

        assertEquals(configured, resolved)
    }

    @Test
    fun `gradle flag overrides versions file`() {
        val resolved = resolveGradle(Gradle.latest().cliValue, VersionsFile(gradle = Gradle.oldest()))

        assertEquals(Gradle.latest(), resolved)
    }

    @Test
    fun `latest gradle is used when neither flag nor versions file provide one`() {
        val resolved = resolveGradle(null, null)

        assertEquals(Gradle.latest(), resolved)
    }

    @Test
    fun `default output path for kts nests project name and project_kts`() {
        val resolved = resolveProjectRootPath(null, Language.KTS, "sample")

        assertEquals("projects_generated/sample/project_kts", resolved)
    }

    @Test
    fun `output dir is used directly for single language projects`() {
        val resolved = resolveProjectRootPath(".", Language.KTS, "sample")

        assertEquals(".", resolved)
    }

    @Test
    fun `default output path for both languages nests project name only`() {
        val resolved = resolveProjectRootPath(null, Language.BOTH, "sample")

        assertEquals("projects_generated/sample", resolved)
    }
}
