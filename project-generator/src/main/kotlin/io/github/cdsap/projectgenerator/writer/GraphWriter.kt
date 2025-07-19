package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.NameMappings
import java.io.File

class GraphWriter(private val nodes: List<ProjectGraph>, val path: String) {

    fun write() {
        println("Creating graph file")
        File("$path/graph.dot").createNewFile()
        var content = "digraph G { \n"
        nodes.forEach { nodeGraph ->
            val node =  "${NameMappings.layerName(nodeGraph.layer)}:${NameMappings.moduleName(nodeGraph.id)}"
            nodeGraph.nodes.forEach { dep ->
                content += "\"$node\" -> \"${NameMappings.layerName(dep.layer)}:${NameMappings.moduleName(dep.id)}\";\n"

            }
        }
        content += "}\n"
        File("$path/graph.dot").writeText(content)
    }
}
