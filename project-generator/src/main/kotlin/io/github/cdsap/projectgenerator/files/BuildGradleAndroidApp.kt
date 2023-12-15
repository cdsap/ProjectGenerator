package io.github.cdsap.projectgenerator.files

class BuildGradleAndroidApp {

    fun get() = """
        plugins {
          id("awesome.androidapp.plugin")
        }
        """.trimIndent()
}
