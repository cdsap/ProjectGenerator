package io.github.cdsap.projectgenerator.generator.plugins.android

import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Project
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CompositeBuildPluginAndroidLintToolchainTest {

    @Test
    fun `agp 9_5 app and lib plugins pin lint to the project jdk`() {
        val versions = Versions(
            project = Project(jdk = "21"),
            android = Android(agp = "9.5.0")
        )

        val app = CompositeBuildPluginAndroidApp().get(versions, DependencyInjection.NONE)
        val lib = CompositeBuildPluginAndroidLib().get(versions, DependencyInjection.NONE)

        listOf(app, lib).forEach { content ->
            assertTrue(
                content.contains("languageVersion.set(JavaLanguageVersion.of(21))"),
                "Expected lint toolchain to use project JDK 21:\n$content"
            )
            assertTrue(content.contains("lint {"), content)
            assertTrue(content.contains("toolchain {"), content)
        }
    }

    @Test
    fun `agp 9_4 app and lib plugins do not emit lint toolchain dsl`() {
        val versions = Versions(
            project = Project(jdk = "21"),
            android = Android(agp = "9.4.1")
        )

        val app = CompositeBuildPluginAndroidApp().get(versions, DependencyInjection.NONE)
        val lib = CompositeBuildPluginAndroidLib().get(versions, DependencyInjection.NONE)

        listOf(app, lib).forEach { content ->
            assertFalse(content.contains("lint {"), content)
            assertFalse(content.contains("toolchain {"), content)
        }
    }
}
