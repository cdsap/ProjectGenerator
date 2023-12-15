package io.github.cdsap.projectgenerator.files

import io.github.cdsap.projectgenerator.model.Versions

class BuildGradle {

    fun get(versions: Versions, dependencyPlugins: Boolean) = """
        plugins {
            id("org.jetbrains.kotlin.jvm") version("${versions.kgp}") apply false
            id("com.android.application") version "${versions.agp}" apply false
            id("com.android.library") version "${versions.agp}" apply false
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

    fun getJvm(versions: Versions) = """
        plugins {
            id("org.jetbrains.kotlin.jvm") version("${versions.kgp}") apply false
        }
        """.trimIndent()
}
