package io.github.cdsap.generator.files

class BuildGradle {

    fun get() = """
        plugins {
            id("com.autonomousapps.dependency-analysis") version "1.21.0"
            id("org.jetbrains.kotlin.jvm") version("1.9.10") apply false
        }
        """.trimIndent()
}
