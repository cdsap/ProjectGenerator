package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.ProjectGraph

/**
 * Immutable layer and module name maps produced by [ProjectNameMappingFactory].
 */
data class ProjectNameMaps(
    val layerNames: Map<Int, String>,
    val moduleNames: Map<String, String>
)

/**
 * Builds layer and module name maps from generation inputs without mutating
 * the global [NameMappings] singleton.
 */
object ProjectNameMappingFactory {

    fun create(
        layers: Int,
        nodes: List<ProjectGraph>,
        layerNames: List<String>,
        moduleNameParts: List<String>
    ): ProjectNameMaps {
        val layerMap = (0..layers).associateWith { index ->
            if (index == layers) {
                "app"
            } else {
                layerNames.getOrNull(index) ?: "layer_$index"
            }
        }

        val moduleMap = nodes
            .sortedBy { it.id.substringAfterLast("_").toInt() }
            .mapIndexed { index, node ->
                if (node.layer == layers) {
                    node.id to "app"
                } else {
                    node.id to generateModuleName(index, moduleNameParts)
                }
            }.toMap()

        return ProjectNameMaps(layerMap, moduleMap)
    }

    private fun generateModuleName(index: Int, moduleNameParts: List<String>): String {
        var remaining = index
        val base = moduleNameParts.size
        val parts = mutableListOf<String>()
        do {
            parts.add(moduleNameParts[remaining % base])
            remaining /= base
        } while (remaining > 0)
        return parts.joinToString("-")
    }
}
