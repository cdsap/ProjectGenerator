package io.github.cdsap.projectgenerator.generator.plugins.android

import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CompositeBuildPluginAndroidCompileSdkTest {
    @ParameterizedTest
    @ValueSource(strings = ["app", "library", "kmp"])
    fun `android plugins compile against API 37 minor 1 required by Compose`(type: String) {
        val versions = Versions()
        val content = when (type) {
            "app" -> CompositeBuildPluginAndroidApp().get(versions, DependencyInjection.NONE)
            "library" -> CompositeBuildPluginAndroidLib().get(versions, DependencyInjection.NONE)
            else -> CompositeBuildPluginAndroidKmpLib().get(versions, DependencyInjection.NONE)
        }
        assertTrue(
            Regex("compileSdk\\s*\\{\\s*version = release\\(37\\)\\s*\\{\\s*minorApiLevel = 1\\s*}")
                .containsMatchIn(content),
            "Expected $type to compile against API 37.1:\n$content"
        )
    }
}
