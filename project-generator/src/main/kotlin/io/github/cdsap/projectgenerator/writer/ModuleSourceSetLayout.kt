package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.generator.android.AndroidSourceSetLayout
import io.github.cdsap.projectgenerator.model.ProjectGraph

interface ModuleSourceSetLayout {
    fun mainKotlinDir(node: ProjectGraph): String
    fun testKotlinDir(node: ProjectGraph): String
}

object JvmModuleSourceSetLayout : ModuleSourceSetLayout {
    override fun mainKotlinDir(node: ProjectGraph): String = "src/main/kotlin"

    override fun testKotlinDir(node: ProjectGraph): String = "src/test/kotlin"
}

class AndroidModuleSourceSetLayout(
    private val kotlinMultiplatformLibrary: Boolean
) : ModuleSourceSetLayout {
    override fun mainKotlinDir(node: ProjectGraph): String =
        AndroidSourceSetLayout.kotlinMainSourceDir(node.type, kotlinMultiplatformLibrary)

    override fun testKotlinDir(node: ProjectGraph): String =
        AndroidSourceSetLayout.kotlinTestSourceDir(node.type, kotlinMultiplatformLibrary)
}
