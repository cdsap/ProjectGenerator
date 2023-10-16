package io.github.cdsap.generator.files

class BuildGradleAndroidLib {

    fun get() = """
        plugins {
          id("awesome.androidlib.plugin")
        }
        """.trimIndent()
}
