package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.MultiUsageError
import com.github.ajalt.clikt.core.parse
import com.github.ajalt.clikt.core.subcommands
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.readText

class ReadmeCliExamplesTest {

    @TempDir
    lateinit var outputDir: Path

    @Test
    fun `maintained README CLI examples run successfully`() {
        val readme = Path.of("..", "README.md").readText()

        listOf(
            "#### Example: Generate a project with custom options",
            "## Generate Unit Test"
        ).forEachIndexed { index, heading ->
            val arguments = readme.commandAfter(heading).split(Regex("\\s+")).drop(1)

            try {
                ProjectReportCli()
                    .subcommands(GenerateProjects(), GenerateYaml())
                    .parse(arguments + listOf("--output-dir", outputDir.resolve("example-$index").toString()))
            } catch (exception: Exception) {
                val message = if (exception is MultiUsageError) {
                    exception.errors.joinToString { it.message.orEmpty() }
                } else {
                    exception.message
                }
                throw AssertionError("README example failed: $heading: $message", exception)
            }
        }
    }

    private fun String.commandAfter(heading: String): String {
        require(heading in this) { "README heading not found: $heading" }
        return substringAfter(heading)
            .substringAfter("```bash")
            .substringBefore("```")
            .trim()
    }
}
