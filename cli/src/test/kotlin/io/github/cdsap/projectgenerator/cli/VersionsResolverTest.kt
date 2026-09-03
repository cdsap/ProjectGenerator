package io.github.cdsap.projectgenerator.cli

import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Project
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.model.VersionsFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class VersionsResolverTest {

    @Test
    fun `resolve uses defaults when versions file is absent`() {
        val resolved = VersionsResolver.resolve(
            fileVersions = null,
            overrides = VersionsOverrides(
                dependencyInjection = DependencyInjection.HILT,
                develocityUrl = null,
                roomDatabase = false,
                kotlinMultiplatformLibrary = false
            )
        )

        assertEquals(Versions(), resolved)
    }

    @Test
    fun `applyTo keeps versions-file values when cli overrides are absent`() {
        val base = VersionsFile(
            project = Project(develocityUrl = "https://develocity.example"),
            android = Android(roomDatabase = true, kotlinMultiplatformLibrary = true),
            di = DependencyInjection.METRO
        ).resolve()

        val resolved = VersionsOverrides(
            dependencyInjection = DependencyInjection.HILT,
            develocityUrl = null,
            roomDatabase = false,
            kotlinMultiplatformLibrary = false
        ).applyTo(base)

        assertEquals("https://develocity.example", resolved.project.develocityUrl)
        assertTrue(resolved.android.roomDatabase)
        assertTrue(resolved.android.kotlinMultiplatformLibrary)
        assertEquals(DependencyInjection.HILT, resolved.di)
    }

    @Test
    fun `applyTo applies cli overrides for develocity room kmp and di`() {
        val base = VersionsFile(
            project = Project(develocityUrl = "https://from-file.example"),
            android = Android(roomDatabase = false, kotlinMultiplatformLibrary = false),
            di = DependencyInjection.HILT
        ).resolve()

        val resolved = VersionsOverrides(
            dependencyInjection = DependencyInjection.NONE,
            develocityUrl = "https://from-cli.example",
            roomDatabase = true,
            kotlinMultiplatformLibrary = true
        ).applyTo(base)

        assertEquals("https://from-cli.example", resolved.project.develocityUrl)
        assertTrue(resolved.android.roomDatabase)
        assertTrue(resolved.android.kotlinMultiplatformLibrary)
        assertEquals(DependencyInjection.NONE, resolved.di)
    }

    @Test
    fun `applyTo does not clear file android flags when cli flags are false`() {
        val base = VersionsFile(
            android = Android(roomDatabase = true, kotlinMultiplatformLibrary = true)
        ).resolve()

        val resolved = VersionsOverrides(
            dependencyInjection = DependencyInjection.METRO,
            develocityUrl = null,
            roomDatabase = false,
            kotlinMultiplatformLibrary = false
        ).applyTo(base)

        assertTrue(resolved.android.roomDatabase)
        assertTrue(resolved.android.kotlinMultiplatformLibrary)
        assertEquals("", resolved.project.develocityUrl)
        assertEquals(DependencyInjection.METRO, resolved.di)
    }
}
