package io.github.cdsap.generator.files

import io.github.cdsap.generator.model.Versions

class BuildGradle {

    fun get(versions: Versions) = """
        plugins {
            id("com.autonomousapps.dependency-analysis") version "1.21.0"
            id("org.jetbrains.kotlin.jvm") version("${versions.kgp}") apply false
            id("com.android.application") version "${versions.agp}" apply false
            id("com.android.library") version "${versions.agp}" apply false
        }
        """.trimIndent()
}
