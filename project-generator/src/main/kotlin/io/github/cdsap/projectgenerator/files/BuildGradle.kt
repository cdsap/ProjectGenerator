package io.github.cdsap.projectgenerator.files

import io.github.cdsap.projectgenerator.model.KotlinProcessor
import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class BuildGradle {

    fun getAndroid(versions: Versions, dependencyPlugins: Boolean) = """
        plugins {
            alias(libs.plugins.kotlin.jvm) apply false
            alias(libs.plugins.kotlin.android) apply false
            alias(libs.plugins.kotlin.compose) apply false
            alias(libs.plugins.android.application) apply false
            alias(libs.plugins.android.library) apply false
           ${ provideKotlinProcessor(versions)}
            alias(libs.plugins.hilt) apply false

            ${dependencyPlugins(dependencyPlugins)}
        }
        """.trimIndent()

    fun getAndroidSimple(versions: Versions, dependencyPlugins: Boolean) = """
        plugins {
            id("org.jetbrains.kotlin.jvm") version("${versions.kotlin.kgp}") apply false
            id("com.android.application") version "${versions.android.agp}" apply false
            id("com.android.library") version "${versions.android.agp}" apply false
            id("org.jetbrains.kotlin.android") version "${versions.kotlin.kgp}" apply false
            ${ provideKotlinProcessor(versions)}

            ${dependencyPlugins(dependencyPlugins)}
        }
        """.trimIndent()

    private fun dependencyPlugins(dependencyPlugins: Boolean): String {
        return if (dependencyPlugins) {
            """
                    id("com.autonomousapps.dependency-analysis") version "1.21.0"
                    id("com.jraska.module.graph.assertion") version "2.5.0"
                    id("net.siggijons.gradle.graphuntangler") version "0.0.4"
                """.trimIndent()
        } else ""
    }

    fun getJvm(versions: Versions, dependencyPlugins: Boolean) = """
        plugins {
            kotlin("jvm") version("${versions.kotlin.kgp}") apply false
            ${dependencyPlugins(dependencyPlugins)}
        }
        """.trimIndent()

    fun provideKotlinProcessor(versions: Versions) = if (versions.kotlin.kotlinProcessor.processor == Processor.KAPT)
        """"""
    else
        """ alias(libs.plugins.kotlin.ksp) apply false"""

}
