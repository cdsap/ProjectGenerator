package io.github.cdsap.projectgenerator

/**
 * Global lookup for layer and module names. When not provided, defaults
 * to the conventional names `layer_<number>` and the original module id.
 */
object NameMappings {
    var layerNames: Map<Int, String> = emptyMap()
    var moduleNames: Map<String, String> = emptyMap()

    fun layerName(layer: Int): String = layerNames[layer] ?: "layer_${layer}"
    fun moduleName(id: String): String = moduleNames[id] ?: id
}
