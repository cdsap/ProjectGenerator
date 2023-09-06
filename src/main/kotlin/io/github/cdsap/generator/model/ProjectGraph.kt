package io.github.cdsap.generator.model

data class ProjectGraph(
    val id: String,
    val layer: Int,
    val nodes: List<String>
)
