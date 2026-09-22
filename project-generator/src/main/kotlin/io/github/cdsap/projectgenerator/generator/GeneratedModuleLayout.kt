package io.github.cdsap.projectgenerator.generator

import io.github.cdsap.projectgenerator.ProjectNameMaps
import io.github.cdsap.projectgenerator.generator.android.AndroidSourceSetLayout
import io.github.cdsap.projectgenerator.model.ModuleClassDefinitionAndroid
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import java.io.File

class GeneratedModuleLayout private constructor(
    private val projectName: String,
    private val layerDir: String,
    private val moduleDir: String,
    private val packageDir: String,
    private val mainKotlinSourceDir: String,
    private val testKotlinSourceDir: String,
    private val resourcesSourceDir: String,
    private val manifestSourceDir: String
) {
    fun mainKotlinPackageDir(): File =
        File("$projectName/$layerDir/$moduleDir/$mainKotlinSourceDir/com/awesomeapp/$packageDir/")

    fun testKotlinPackageDir(): File =
        File("$projectName/$layerDir/$moduleDir/$testKotlinSourceDir/com/awesomeapp/$packageDir/")

    fun resourcesLayoutDir(): File =
        File("$projectName/$layerDir/$moduleDir/$resourcesSourceDir/layout")

    fun resourcesValuesDir(): File =
        File("$projectName/$layerDir/$moduleDir/$resourcesSourceDir/values")

    fun manifestDir(): File =
        File("$projectName/$layerDir/$moduleDir/$manifestSourceDir/")

    companion object {
        fun of(
            projectName: String,
            node: ProjectGraph,
            kotlinMultiplatformLibrary: Boolean,
            nameMaps: ProjectNameMaps
        ): GeneratedModuleLayout =
            of(
                projectName = projectName,
                layer = node.layer,
                moduleId = node.id,
                type = node.type,
                kotlinMultiplatformLibrary = kotlinMultiplatformLibrary,
                nameMaps = nameMaps
            )

        fun of(
            projectName: String,
            moduleDefinition: ModuleClassDefinitionAndroid,
            kotlinMultiplatformLibrary: Boolean,
            nameMaps: ProjectNameMaps
        ): GeneratedModuleLayout =
            of(
                projectName = projectName,
                layer = moduleDefinition.layer,
                moduleId = moduleDefinition.moduleId,
                type = moduleDefinition.projectType ?: TypeProject.ANDROID_LIB,
                kotlinMultiplatformLibrary = kotlinMultiplatformLibrary,
                nameMaps = nameMaps
            )

        fun of(
            projectName: String,
            node: ProjectGraph,
            mainKotlinSourceDir: String,
            testKotlinSourceDir: String,
            nameMaps: ProjectNameMaps,
            kotlinMultiplatformLibrary: Boolean = false
        ): GeneratedModuleLayout =
            GeneratedModuleLayout(
                projectName = projectName,
                layerDir = layerDir(nameMaps, node.layer),
                moduleDir = moduleDir(nameMaps, node.id),
                packageDir = packageDir(nameMaps, node.id),
                mainKotlinSourceDir = mainKotlinSourceDir,
                testKotlinSourceDir = testKotlinSourceDir,
                resourcesSourceDir = AndroidSourceSetLayout.resourcesSourceDir(
                    node.type,
                    kotlinMultiplatformLibrary
                ),
                manifestSourceDir = AndroidSourceSetLayout.manifestSourceDir(
                    node.type,
                    kotlinMultiplatformLibrary
                )
            )

        private fun of(
            projectName: String,
            layer: Int,
            moduleId: String,
            type: TypeProject,
            kotlinMultiplatformLibrary: Boolean,
            nameMaps: ProjectNameMaps
        ): GeneratedModuleLayout =
            GeneratedModuleLayout(
                projectName = projectName,
                layerDir = layerDir(nameMaps, layer),
                moduleDir = moduleDir(nameMaps, moduleId),
                packageDir = packageDir(nameMaps, moduleId),
                mainKotlinSourceDir = AndroidSourceSetLayout.kotlinMainSourceDir(
                    type,
                    kotlinMultiplatformLibrary
                ),
                testKotlinSourceDir = AndroidSourceSetLayout.kotlinTestSourceDir(
                    type,
                    kotlinMultiplatformLibrary
                ),
                resourcesSourceDir = AndroidSourceSetLayout.resourcesSourceDir(
                    type,
                    kotlinMultiplatformLibrary
                ),
                manifestSourceDir = AndroidSourceSetLayout.manifestSourceDir(
                    type,
                    kotlinMultiplatformLibrary
                )
            )

        private fun layerDir(nameMaps: ProjectNameMaps, layer: Int): String =
            nameMaps.layerNames[layer] ?: "layer_$layer"

        private fun moduleDir(nameMaps: ProjectNameMaps, moduleId: String): String =
            nameMaps.moduleNames[moduleId] ?: moduleId

        private fun packageDir(nameMaps: ProjectNameMaps, moduleId: String): String =
            moduleDir(nameMaps, moduleId).replace("-", "")
    }
}
