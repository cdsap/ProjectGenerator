package io.github.cdsap.generator.files

class BuildGradleAndroidApp {

    fun get() = """
            plugins {
               id("awesome.androidapp.plugin")
            }
        """.trimIndent()
}
