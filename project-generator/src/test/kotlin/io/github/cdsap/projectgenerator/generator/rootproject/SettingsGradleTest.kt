package io.github.cdsap.projectgenerator.generator.rootproject

import io.github.cdsap.projectgenerator.model.AdditionalPlugin
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SettingsGradleTest {

    @Test
    fun `does not include plugins block when additionalSettingsPlugins is empty and develocity is false`() {
        val versions = Versions(additionalSettingsPlugins = emptyList())
        val result = SettingsGradle().get(versions, develocity = false, projectName = "testProject")

        assertFalse(
            result.contains("plugins {"),
            "settings.gradle.kts should not contain plugins block when additionalSettingsPlugins is empty and develocity is false"
        )
        assertFalse(
            result.contains("spotlight") || result.contains("com.fueledbycaffeine.spotlight"),
            "settings.gradle.kts should not contain Spotlight when additionalSettingsPlugins is empty"
        )
    }

    @Test
    fun `includes settings plugins when additionalSettingsPlugins is provided`() {
        val versions = Versions(
            additionalSettingsPlugins = listOf(
                AdditionalPlugin("com.fueledbycaffeine.spotlight", "1.4.1")
            )
        )
        val result = SettingsGradle().get(versions, develocity = false, projectName = "testProject")

        assertTrue(result.contains("plugins {"))
        assertTrue(result.contains("com.fueledbycaffeine.spotlight"))
        assertTrue(result.contains("1.4.1"))
    }

    @Test
    fun `renders module include statements for mapped paths`() {
        val result = SettingsGradle().get(
            Versions(),
            develocity = false,
            projectName = "testProject",
            modulePaths = listOf("layer_1:sample-lib", "app:app")
        )

        assertTrue(result.contains("include (\":layer_1:sample-lib\")"))
        assertTrue(result.contains("include (\":app:app\")"))
    }

    @Test
    fun `preserves module include ordering`() {
        val result = SettingsGradle().get(
            Versions(),
            develocity = false,
            projectName = "testProject",
            modulePaths = listOf("layer_0:alpha", "layer_1:beta", "app:app")
        )

        val alpha = result.indexOf("include (\":layer_0:alpha\")")
        val beta = result.indexOf("include (\":layer_1:beta\")")
        val app = result.indexOf("include (\":app:app\")")

        assertTrue(alpha >= 0)
        assertTrue(beta > alpha)
        assertTrue(app > beta)
    }

    @Test
    fun `appends includes after settings header with legacy spacing`() {
        val withModules = SettingsGradle().get(
            Versions(),
            develocity = false,
            projectName = "testProject",
            modulePaths = listOf("layer_1:lib")
        )

        assertTrue(withModules.contains("} \ninclude (\":layer_1:lib\")"))
        assertTrue(withModules.endsWith("include (\":layer_1:lib\")"))
    }
}
