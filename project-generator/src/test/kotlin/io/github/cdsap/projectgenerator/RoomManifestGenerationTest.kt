package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.ClassesPerModuleType
import io.github.cdsap.projectgenerator.model.Language
import io.github.cdsap.projectgenerator.model.Project
import io.github.cdsap.projectgenerator.model.Shape
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class RoomManifestGenerationTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `room project with default class count generates valid launcher activity name`() {
        val projectName = "room_manifest_low_classes"
        ProjectGenerator(
            modules = 20,
            shape = Shape.FLAT,
            language = Language.KTS,
            typeOfProjectRequested = TypeProjectRequested.ANDROID,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 5),
            versions = Versions(
                project = Project(jdk = "17"),
                android = Android(roomDatabase = true)
            ),
            typeOfStringResources = TypeOfStringResources.NORMAL,
            layers = 3,
            generateUnitTest = false,
            gradle = GradleWrapper(DefaultTestVersions.LATEST_GRADLE),
            path = tempDir.toString(),
            projectName = projectName
        ).write()

        val projectDir = tempDir.resolve("$projectName/project_kts").toFile()
        val manifest = projectDir.walkTopDown()
            .firstOrNull { file ->
                file.isFile &&
                    file.name == "AndroidManifest.xml" &&
                    file.readText().contains("android.intent.category.LAUNCHER")
            }
            ?: error("Could not find launcher manifest under ${projectDir.path}")
        val manifestText = manifest.readText()

        assertFalse(manifestText.contains("android:name=\".\""))
        assertTrue(manifestText.contains("android:name=\".Activity"))
    }
}
