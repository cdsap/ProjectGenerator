package io.github.cdsap.projectgenerator

import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Versions

class DefaultTestVersions {
    companion object {
        val LATEST_GRADLE = Gradle.latest()
        val OLDEST_SUPPORTED_GRADLE = Gradle.oldest()
    }
}
