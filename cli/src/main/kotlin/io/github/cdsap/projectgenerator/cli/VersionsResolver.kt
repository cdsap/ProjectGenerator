package io.github.cdsap.projectgenerator.cli

import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.model.VersionsFile

// CLI-only: false / null means "do not override" so --versions-file values stay intact.
// dependencyInjection is always taken from the CLI (Clikt always supplies a value).
data class VersionsOverrides(
    val dependencyInjection: DependencyInjection,
    val develocityUrl: String?,
    val roomDatabase: Boolean,
    val kotlinMultiplatformLibrary: Boolean
) {
    fun applyTo(base: Versions): Versions {
        var androidConfig = base.android
        if (roomDatabase) {
            androidConfig = androidConfig.copy(roomDatabase = true)
        }
        if (kotlinMultiplatformLibrary) {
            androidConfig = androidConfig.copy(kotlinMultiplatformLibrary = true)
        }
        val withAndroidFlags = base.copy(android = androidConfig, di = dependencyInjection)
        return if (develocityUrl != null) {
            withAndroidFlags.copy(project = withAndroidFlags.project.copy(develocityUrl = develocityUrl))
        } else {
            withAndroidFlags
        }
    }
}

object VersionsResolver {
    fun resolve(
        fileVersions: VersionsFile?,
        overrides: VersionsOverrides
    ): Versions {
        val base = fileVersions?.resolve() ?: Versions()
        return overrides.applyTo(base)
    }

    fun resolveGradle(cliGradle: String?, fileVersions: VersionsFile?): Gradle {
        return cliGradle?.let(Gradle::fromValue)
            ?: fileVersions?.gradle
            ?: Gradle.latest()
    }
}
