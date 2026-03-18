package io.github.cdsap.projectgenerator.generator.plugins.jvm

import io.github.cdsap.projectgenerator.model.Kotlin
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CompositeBuildJvmLibTest {

    @Test
    fun `uses configured kotlin test version in jvm convention plugin`() {
        val versions = Versions(kotlin = Kotlin(kgp = "2.1.21"))

        val content = CompositeBuildJvmLib().get(versions)

        assertTrue(content.contains("org.jetbrains.kotlin:kotlin-test:2.1.21"))
    }
}
