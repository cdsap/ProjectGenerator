package io.github.cdsap.projectgenerator.cli

import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.model.VersionsFile

object VersionsResolver {
    fun resolve(
        fileVersions: VersionsFile?,
        dependencyInjection: DependencyInjection,
        develocityUrl: String?,
        roomDatabase: Boolean,
        kotlinMultiplatformLibrary: Boolean
    ): Versions {
        val versions = if (fileVersions != null) {
            fileVersions.resolve()
        } else {
            Versions()
        }
        // CLI flags only enable features; false must not clear values from --versions-file.
        var androidConfig = versions.android
        if (roomDatabase) {
            androidConfig = androidConfig.copy(roomDatabase = true)
        }
        if (kotlinMultiplatformLibrary) {
            androidConfig = androidConfig.copy(kotlinMultiplatformLibrary = true)
        }
        val withAndroidFlags = versions.copy(android = androidConfig, di = dependencyInjection)
        return if (develocityUrl != null) {
            withAndroidFlags.copy(project = withAndroidFlags.project.copy(develocityUrl = develocityUrl))
        } else {
            withAndroidFlags
        }
    }
}
