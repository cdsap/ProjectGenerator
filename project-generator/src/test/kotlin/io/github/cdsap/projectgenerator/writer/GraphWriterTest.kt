package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.ProjectNameMaps
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class GraphWriterTest {

    @TempDir
    lateinit var tempDir: Path

    private val nameMaps = ProjectNameMaps(
        layerNames = mapOf(1 to "layer_1", 2 to "app"),
        moduleNames = mapOf("module_1_1" to "sample-lib", "module_2_1" to "app")
    )

    @Test
    fun `render builds digraph edges with layer module labels from ProjectNameMaps`() {
        val lib = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.LIB, 1)
        val app = ProjectGraph("module_2_1", 2, listOf(lib), TypeProject.ANDROID_APP, 1)

        val dot = GraphWriter.render(listOf(lib, app), nameMaps)

        assertEquals(
            """
            digraph G {
            "app:app" -> "layer_1:sample-lib";
            }

            """.trimIndent(),
            dot
        )
    }

    @Test
    fun `write writes render output to graph dot`() {
        val lib = ProjectGraph("module_1_1", 1, emptyList(), TypeProject.LIB, 1)
        val app = ProjectGraph("module_2_1", 2, listOf(lib), TypeProject.ANDROID_APP, 1)
        val nodes = listOf(lib, app)
        val outDir = tempDir.resolve("out").toFile().also { it.mkdirs() }

        GraphWriter(nodes, outDir.path, nameMaps).write()

        val graphFile = File(outDir, "graph.dot")
        assertTrue(graphFile.exists())
        assertEquals(GraphWriter.render(nodes, nameMaps), graphFile.readText())
    }
}
