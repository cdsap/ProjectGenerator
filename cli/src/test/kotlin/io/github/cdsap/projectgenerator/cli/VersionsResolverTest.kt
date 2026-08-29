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
            dependencyInjection = DependencyInjection.HILT,
            develocityUrl = null,
            roomDatabase = false,
            kotlinMultiplatformLibrary = false
        )

        assertEquals(Versions(), resolved)
    }

    @Test
    fun `resolve keeps versions-file values when cli overrides are absent`() {
        val fileVersions = VersionsFile(
            project = Project(develocityUrl = "https://develocity.example"),
            android = Android(roomDatabase = true, kotlinMultiplatformLibrary = true),
            di = DependencyInjection.METRO
        )

        val resolved = VersionsResolver.resolve(
            fileVersions = fileVersions,
            dependencyInjection = DependencyInjection.HILT,
            develocityUrl = null,
            roomDatabase = false,
            kotlinMultiplatformLibrary = false
        )

        assertEquals("https://develocity.example", resolved.project.develocityUrl)
        assertTrue(resolved.android.roomDatabase)
        assertTrue(resolved.android.kotlinMultiplatformLibrary)
        assertEquals(DependencyInjection.HILT, resolved.di)
    }

    @Test
    fun `resolve applies cli overrides for develocity room kmp and di`() {
        val fileVersions = VersionsFile(
            project = Project(develocityUrl = "https://from-file.example"),
            android = Android(roomDatabase = false, kotlinMultiplatformLibrary = false),
            di = DependencyInjection.HILT
        )

        val resolved = VersionsResolver.resolve(
            fileVersions = fileVersions,
            dependencyInjection = DependencyInjection.NONE,
            develocityUrl = "https://from-cli.example",
            roomDatabase = true,
            kotlinMultiplatformLibrary = true
        )

        assertEquals("https://from-cli.example", resolved.project.develocityUrl)
        assertTrue(resolved.android.roomDatabase)
        assertTrue(resolved.android.kotlinMultiplatformLibrary)
        assertEquals(DependencyInjection.NONE, resolved.di)
    }

    @Test
    fun `resolve does not clear file android flags when cli flags are false`() {
        val fileVersions = VersionsFile(
            android = Android(roomDatabase = true, kotlinMultiplatformLibrary = true)
        )

        val resolved = VersionsResolver.resolve(
            fileVersions = fileVersions,
            dependencyInjection = DependencyInjection.METRO,
            develocityUrl = null,
            roomDatabase = false,
            kotlinMultiplatformLibrary = false
        )

        assertTrue(resolved.android.roomDatabase)
        assertTrue(resolved.android.kotlinMultiplatformLibrary)
        assertEquals("", resolved.project.develocityUrl)
        assertEquals(DependencyInjection.METRO, resolved.di)
    }
}
