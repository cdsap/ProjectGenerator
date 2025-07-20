package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.NameMappings
import java.io.File

class GraphWriter(private val nodes: List<ProjectGraph>, val path: String) {

    fun write() {
        println("Creating graph file")
        val file = File("$path/graph.dot")
        file.createNewFile()

        val content = StringBuilder()
        content.appendLine("digraph G {")

        for (nodeGraph in nodes) {
            val node = "${NameMappings.layerName(nodeGraph.layer)}:${NameMappings.moduleName(nodeGraph.id)}"
            for (dep in nodeGraph.nodes) {
                content.appendLine("\"$node\" -> \"${NameMappings.layerName(dep.layer)}:${NameMappings.moduleName(dep.id)}\";")
            }
        }

        content.appendLine("}")
        file.writeText(content.toString())
    }
}
