package io.github.cdsap.projectgenerator.generator.rootproject

import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.Kotlin
import io.github.cdsap.projectgenerator.model.KotlinProcessor
import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BuildGradleTest {
    @Test
    fun `android root build file omits kotlin android plugin for agp9 built in kotlin`() {
        val result = BuildGradle().getAndroid(Versions(), Versions().di)

        assertFalse(result.contains("libs.plugins.kotlin.android"))
    }

    @Test
    fun `android root build file keeps kotlin android plugin before agp9`() {
        val result = BuildGradle().getAndroid(Versions(android = Android(agp = "8.13.0")), Versions().di)

        assertTrue(result.contains("alias(libs.plugins.kotlin.android) apply false"))
    }

    @Test
    fun `android root build file omits ksp plugin for kapt`() {
        val versions = Versions(kotlin = Kotlin(kotlinProcessor = KotlinProcessor(Processor.KAPT)))

        val result = BuildGradle().getAndroid(versions, versions.di)

        assertFalse(result.contains("libs.plugins.kotlin.ksp"))
    }
}
