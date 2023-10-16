package io.github.cdsap.generator.model

data class ProjectGraph(
    val id: String,
    val layer: Int,
    val nodes: List<ProjectGraph>,
    val type: TypeProject,
    val classes: Int
)
