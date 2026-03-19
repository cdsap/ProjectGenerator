package io.github.cdsap.projectgenerator.generator.rootproject

import io.github.cdsap.projectgenerator.model.AdditionalPlugin
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SettingsGradleTest {

    @Test
    fun `includes default settings plugins when using default versions`() {
        val result = SettingsGradle().get(Versions(), develocity = false, projectName = "testProject")

        assertTrue(
            result.contains("plugins {"),
            "settings.gradle.kts should contain a plugins block when default settings plugins are configured"
        )
        assertTrue(
            result.contains("org.gradle.toolchains.foojay-resolver-convention"),
            "settings.gradle.kts should include the Foojay toolchain resolver plugin by default"
        )
        assertTrue(
            result.contains("com.fueledbycaffeine.spotlight"),
            "settings.gradle.kts should include Spotlight by default"
        )
    }

    @Test
    fun `includes settings plugins when additionalSettingsPlugins is provided`() {
        val versions = Versions(
            additionalSettingsPlugins = listOf(
                AdditionalPlugin("org.gradle.toolchains.foojay-resolver-convention", "1.0.0"),
                AdditionalPlugin("com.fueledbycaffeine.spotlight", "1.4.1")
            )
        )
        val result = SettingsGradle().get(versions, develocity = false, projectName = "testProject")

        assertTrue(result.contains("plugins {"))
        assertTrue(result.contains("org.gradle.toolchains.foojay-resolver-convention"))
        assertTrue(result.contains("com.fueledbycaffeine.spotlight"))
        assertTrue(result.contains("1.4.1"))
    }

    @Test
    fun `does not include foojay or spotlight when additionalSettingsPlugins is empty`() {
        val versions = Versions(additionalSettingsPlugins = emptyList())

        val result = SettingsGradle().get(versions, develocity = false, projectName = "testProject")

        assertFalse(
            result.contains("org.gradle.toolchains.foojay-resolver-convention"),
            "settings.gradle.kts should not contain Foojay when additionalSettingsPlugins is empty"
        )
        assertFalse(
            result.contains("spotlight") || result.contains("com.fueledbycaffeine.spotlight"),
            "settings.gradle.kts should not contain Spotlight when additionalSettingsPlugins is empty"
        )
    }
}
