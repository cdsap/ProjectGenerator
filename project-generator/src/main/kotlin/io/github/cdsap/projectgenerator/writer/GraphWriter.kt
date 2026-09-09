package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.ProjectNameMaps
import io.github.cdsap.projectgenerator.model.ProjectGraph
import java.io.File

class GraphWriter(
    private val nodes: List<ProjectGraph>,
    val path: String,
    private val nameMaps: ProjectNameMaps
) {

    fun write() {
        println("Creating graph file")
        val file = File("$path/graph.dot")
        file.createNewFile()
        file.writeText(render(nodes, nameMaps))
    }

    companion object {
        fun render(nodes: List<ProjectGraph>, nameMaps: ProjectNameMaps): String {
            val content = StringBuilder()
            content.appendLine("digraph G {")

            for (nodeGraph in nodes) {
                val node = "${layerName(nameMaps, nodeGraph.layer)}:${moduleName(nameMaps, nodeGraph.id)}"
                for (dep in nodeGraph.nodes) {
                    content.appendLine(
                        "\"$node\" -> \"${layerName(nameMaps, dep.layer)}:${moduleName(nameMaps, dep.id)}\";"
                    )
                }
            }

            content.appendLine("}")
            return content.toString()
        }

        private fun layerName(nameMaps: ProjectNameMaps, layer: Int): String =
            nameMaps.layerNames[layer] ?: "layer_$layer"

        private fun moduleName(nameMaps: ProjectNameMaps, id: String): String =
            nameMaps.moduleNames[id] ?: id
    }
}
