package io.github.cdsap.generator.files

class BuildGradleLib {

    fun get() = """
        plugins {
               id ("java-library")
               id ("maven-publish")
               id ("jacoco")
               id ("org.sonarqube") version "4.3.0.3225"
               id("awesome.kotlin.plugin")
            }
        """.trimIndent()
}
