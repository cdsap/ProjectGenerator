package io.github.cdsap.projectgenerator.generator.toml

import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AndroidTomlTest {
    @Test
    fun `generates TOML with expected keys and values`() {
        val versions = Versions() // uses default values
        val toml = AndroidToml().toml(versions)
        Assertions.assertTrue(toml.contains("[versions]"), "Should contain [versions] section")
        Assertions.assertTrue(
            toml.contains("androidx-core = \"${versions.android.androidxCore}\""),
            "Should contain androidx-core version"
        )
        Assertions.assertTrue(toml.contains("[libraries]"), "Should contain [libraries] section")
        Assertions.assertTrue(
            toml.contains("androidx-core-ktx = { group = \"androidx.core\", name = \"core-ktx\", version.ref = \"androidx-core\" }"),
            "Should contain androidx-core-ktx library"
        )
        Assertions.assertTrue(
            toml.contains("compose-bom = { group = \"androidx.compose\", name = \"compose-bom\", version.ref = \"compose-bom\" }"),
            "Should contain compose-bom library"
        )
        Assertions.assertTrue(
            toml.contains("junit4 = { group = \"junit\", name = \"junit\", version.ref = \"junit4\" }"),
            "Should contain junit4 library"
        )
    }
}
