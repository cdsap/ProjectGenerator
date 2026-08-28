package io.github.cdsap.projectgenerator.generator

import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.generator.android.AndroidSourceSetLayout
import io.github.cdsap.projectgenerator.model.ModuleClassDefinitionAndroid
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.model.TypeProject
import java.io.File

class GeneratedModuleLayout private constructor(
    private val projectName: String,
    private val layer: Int,
    private val moduleId: String,
    private val mainKotlinSourceDir: String,
    private val testKotlinSourceDir: String,
    private val resourcesSourceDir: String,
    private val manifestSourceDir: String
) {
    private val layerDir: String = NameMappings.layerName(layer)
    private val moduleDir: String = NameMappings.moduleName(moduleId)
    private val packageDir: String = NameMappings.modulePackageName(moduleId)

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
            kotlinMultiplatformLibrary: Boolean
        ): GeneratedModuleLayout =
            of(
                projectName = projectName,
                layer = node.layer,
                moduleId = node.id,
                type = node.type,
                kotlinMultiplatformLibrary = kotlinMultiplatformLibrary
            )

        fun of(
            projectName: String,
            moduleDefinition: ModuleClassDefinitionAndroid,
            kotlinMultiplatformLibrary: Boolean
        ): GeneratedModuleLayout =
            of(
                projectName = projectName,
                layer = moduleDefinition.layer,
                moduleId = moduleDefinition.moduleId,
                type = moduleDefinition.projectType ?: TypeProject.ANDROID_LIB,
                kotlinMultiplatformLibrary = kotlinMultiplatformLibrary
            )

        fun of(
            projectName: String,
            node: ProjectGraph,
            mainKotlinSourceDir: String,
            testKotlinSourceDir: String,
            kotlinMultiplatformLibrary: Boolean = false
        ): GeneratedModuleLayout =
            GeneratedModuleLayout(
                projectName = projectName,
                layer = node.layer,
                moduleId = node.id,
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
            kotlinMultiplatformLibrary: Boolean
        ): GeneratedModuleLayout =
            GeneratedModuleLayout(
                projectName = projectName,
                layer = layer,
                moduleId = moduleId,
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
    }
}
