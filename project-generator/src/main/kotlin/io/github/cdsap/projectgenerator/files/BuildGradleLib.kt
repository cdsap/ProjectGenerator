package io.github.cdsap.projectgenerator.files

class BuildGradleLib {

    fun get() = """
        plugins {
            id ("java-library")
            id ("maven-publish")
            id ("jacoco")
            id("awesome.kotlin.plugin")
        }"""
}
