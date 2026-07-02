package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.ClassesPerModuleType
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Language
import io.github.cdsap.projectgenerator.model.Shape
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.readText

class GeneratedKotlinFormattingTest {
    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `generated Kotlin files have consistent whitespace and known block indentation`() {
        val jvmProject = generateProject("jvm", TypeProjectRequested.JVM, Shape.TRIANGLE)
        val hiltProject = generateProject("android-hilt", TypeProjectRequested.ANDROID, Shape.INVERSE_TRIANGLE)
        val kmpProject = generateProject(
            "android-kmp",
            TypeProjectRequested.ANDROID,
            Shape.FLAT,
            Versions(
                android = Android(roomDatabase = true, kotlinMultiplatformLibrary = true),
                di = DependencyInjection.NONE
            )
        )

        listOf(jvmProject, hiltProject, kmpProject).forEach(::assertCleanKotlinWhitespace)

        val jvmPlugin = jvmProject.resolve("build-logic/convention/src/main/kotlin/com/logic/PluginJvmLib.kt").readText()
        assertTrue(jvmPlugin.contains("            toolchain.languageVersion.set("))
        assertTrue(jvmPlugin.contains("add(\"implementation\", \"org.jetbrains.kotlinx"))

        val androidPlugin =
            hiltProject.resolve("build-logic/convention/src/main/kotlin/com/logic/CompositeBuildPluginAndroidApp.kt")
                .readText()
        assertTrue(androidPlugin.contains("            val toolchains ="))
        assertTrue(androidPlugin.contains("            target.tasks.withType("))
        assertTrue(androidPlugin.contains("target.name.replace(\":\", \"_\")"))

        val application = hiltProject.toFile().walkTopDown().first { it.name == "MainApplication.kt" }.readText()
        assertTrue(application.contains("class MainApplication : Application() {"))
    }

    private fun generateProject(
        name: String,
        type: TypeProjectRequested,
        shape: Shape,
        versions: Versions = Versions()
    ): Path {
        val output = tempDir.resolve(name)
        ProjectGenerator(
            modules = 6,
            shape = shape,
            language = Language.KTS,
            typeOfProjectRequested = type,
            classesPerModule = ClassesPerModule(ClassesPerModuleType.FIXED, 10),
            versions = versions,
            layers = 5,
            generateUnitTest = true,
            projectRootPath = output.toString(),
            projectName = name
        ).write()
        return output
    }

    private fun assertCleanKotlinWhitespace(project: Path) {
        val malformedLines = project.toFile().walkTopDown()
            .filter { it.isFile && it.extension in setOf("kt", "kts") }
            .flatMap { file ->
                file.readLines().mapIndexedNotNull { index, line ->
                    if (line.isNotEmpty() && line.last().isWhitespace()) "${file.path}:${index + 1}" else null
                }
            }
            .toList()

        assertTrue(malformedLines.isEmpty()) { "Trailing whitespace found at ${malformedLines.joinToString()}" }
    }
}
