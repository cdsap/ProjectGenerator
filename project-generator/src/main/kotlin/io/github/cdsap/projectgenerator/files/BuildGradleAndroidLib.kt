package io.github.cdsap.projectgenerator.files

class BuildGradleAndroidLib {

    fun get() = """
        plugins {
          id("awesome.androidlib.plugin")
        }
        """.trimIndent()
}
