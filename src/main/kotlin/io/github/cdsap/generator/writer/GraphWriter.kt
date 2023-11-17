package io.github.cdsap.generator.writer

import io.github.cdsap.generator.model.ProjectGraph
import java.io.File

class GraphWriter(private val nodes: List<ProjectGraph>, val path: String) {

    fun write() {
        println("Creating graph file")
        File("$path/graph.dot").createNewFile()
        var content = "digraph G { \n"
        nodes.forEach {
            val node = it.id
            it.nodes.forEach {
                content += "$node -> ${it.id};\n"

            }
        }
        content += "}\n"
        File("$path/graph.dot").writeText(content)
    }
}
