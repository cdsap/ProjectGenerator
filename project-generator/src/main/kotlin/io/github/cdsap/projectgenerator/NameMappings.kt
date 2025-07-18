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

    /**
     * Returns a module name sanitized for use in package declarations.
     * Currently this simply strips dashes from the mapped module name.
     */
    fun modulePackageName(id: String): String = moduleName(id).replace("-", "")
}
