package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.parse
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
}
