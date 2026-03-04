package io.github.cdsap.projectgenerator.generator.android

import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryAndroid
import io.github.cdsap.projectgenerator.model.TypeProject
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

class ManifestTest {

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `android app manifest falls back to valid activity name when dictionary has no activity`() {
        val manifestDir = tempDir.resolve("feature").toFile()
        val dictionary = ConcurrentHashMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>()

        Manifest().createManifest(
            moduleFolder = manifestDir,
            layer = 0,
            module = "account",
            moduleType = TypeProject.ANDROID_APP,
            a = dictionary
        )

        val manifestText = manifestDir.resolve("AndroidManifest.xml").readText()
        assertTrue(manifestText.contains("android:name=\"android.app.Activity\""))
    }
}
