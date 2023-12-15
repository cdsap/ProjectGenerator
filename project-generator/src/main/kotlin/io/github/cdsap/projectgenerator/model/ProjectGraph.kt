package io.github.cdsap.projectgenerator.model

data class ProjectGraph(
    val id: String,
    val layer: Int,
    val nodes: List<ProjectGraph>,
    val type: TypeProject,
    val classes: Int
)
