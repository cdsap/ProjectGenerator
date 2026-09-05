package io.github.cdsap.projectgenerator.generator.rootproject

import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class GradleProperties {
    fun get(versions: Versions, gradle: Gradle = Gradle.latest()): String {
        return buildList {
            add("org.gradle.jvmargs=-Xmx5g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8")
            add("android.useAndroidX=true")
            add("org.gradle.caching=true")
            add("dependency.analysis.compatibility=NONE")
            if (versions.kotlin.kotlinProcessor.processor == Processor.KSP) {
                // Disable K2 for KSP 2.0
                add("ksp.useKSP2=false")
            }
            if (isGradle97(gradle)) {
                // Isolated Projects + KSP IP-compatible task wiring (Gradle 9.7 only)
                add("org.gradle.unsafe.isolated-projects=true")
                add("ksp.project.isolation.enabled=true")
            }
        }.joinToString("\n")
    }

    private fun isGradle97(gradle: Gradle): Boolean {
        val parts = gradle.version.split('.')
        return parts.size >= 2 && parts[0] == "9" && parts[1] == "7"
    }
}
