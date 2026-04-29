package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.parse
import io.github.cdsap.projectgenerator.ProjectGeneratorVersion
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Language
import io.github.cdsap.projectgenerator.model.VersionsFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GenerateProjectsCliTest {

    @Test
    fun `version flag prints the library version`() {
        val message = assertThrows<PrintMessage> {
            ProjectReportCli().parse(listOf("--version"))
        }

        assertEquals("${ProjectReportCli().commandName} version ${ProjectGeneratorVersion.value}", message.message)
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
