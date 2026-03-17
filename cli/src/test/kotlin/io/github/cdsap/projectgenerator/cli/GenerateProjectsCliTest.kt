package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.parse
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.VersionsFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GenerateProjectsCliTest {

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
    fun `gradle from versions file is used when flag is absent`() {
        val resolved = resolveGradle(null, VersionsFile(gradle = Gradle.GRADLE_9_3_1))

        assertEquals(Gradle.GRADLE_9_3_1, resolved)
    }

    @Test
    fun `gradle flag overrides versions file`() {
        val resolved = resolveGradle("gradle_9_4_0", VersionsFile(gradle = Gradle.GRADLE_9_3_1))

        assertEquals(Gradle.GRADLE_9_4_0, resolved)
    }

    @Test
    fun `latest gradle is used when neither flag nor versions file provide one`() {
        val resolved = resolveGradle(null, null)

        assertEquals(Gradle.GRADLE_9_4_0, resolved)
    }
}
