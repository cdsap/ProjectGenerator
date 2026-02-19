package io.github.cdsap.projectgenerator.generator.rootproject

import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class BuildGradle {

    fun getAndroid(versions: Versions, di: DependencyInjection) = """
        plugins {
            alias(libs.plugins.kotlin.jvm) apply false
            alias(libs.plugins.kotlin.android) apply false
            alias(libs.plugins.kotlin.compose) apply false
            alias(libs.plugins.android.application) apply false
            ${androidLibraryRootPlugin(versions)}
            ${provideKotlinProcessor(versions)}
            ${diPlugins(di)}
            ${additionalBuildGradlePlugins(versions)}
        }
        """.trimIndent()


    fun getJvm(versions: Versions) = """
        plugins {
            kotlin("jvm") version("${versions.kotlin.kgp}") apply false
            ${additionalBuildGradlePlugins(versions)}
        }
        """.trimIndent()

    fun provideKotlinProcessor(versions: Versions) = if (versions.kotlin.kotlinProcessor.processor == Processor.KAPT)
        """"""
    else
        """alias(libs.plugins.kotlin.ksp) apply false"""

    fun diPlugins(di: DependencyInjection): String {
        return when (di) {
            DependencyInjection.HILT -> "alias(libs.plugins.hilt) apply false"
            DependencyInjection.METRO -> "alias(libs.plugins.metro) apply false"
            DependencyInjection.NONE -> ""
        }
    }

    fun additionalBuildGradlePlugins(versions: Versions): String {
        var additionalPlugins = ""
        versions.additionalBuildGradleRootPlugins.forEach {
            additionalPlugins += "id(\"${it.id}\") version \"${it.version}\" apply ${it.apply}\n"
        }
        return additionalPlugins
    }

    private fun androidLibraryRootPlugin(versions: Versions): String {
        return if (versions.android.kotlinMultiplatformLibrary) {
            "alias(libs.plugins.android.kotlin.multiplatform.library) apply false"
        } else {
            "alias(libs.plugins.android.library) apply false"
        }
    }

}
