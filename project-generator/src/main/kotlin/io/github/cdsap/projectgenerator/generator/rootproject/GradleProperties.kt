package io.github.cdsap.projectgenerator.generator.rootproject

import io.github.cdsap.projectgenerator.generator.extension.isAgp9
import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class GradleProperties {
    fun get(versions: Versions) = """
        org.gradle.jvmargs=-Xmx5g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
        android.useAndroidX=true
        org.gradle.caching=true
        dependency.analysis.compatibility=NONE
        ${k2usage(versions)}
        ${disableNewDslInAGP9BecauseHilt(versions)}
    """.trimIndent()

    // Disable K2 for KSP 2.0
    private fun k2usage(versions: Versions): String {
        return if (versions.kotlin.kotlinProcessor.processor == Processor.KSP) {
            "ksp.useKSP2=false"
        } else ""
    }

    // Hilt is not compatible with AGP9, if AGP9 is enabled we need to disable android.newDsl=false
    private fun disableNewDslInAGP9BecauseHilt(versions: Versions): String {
        return if (versions.android.agp.isAgp9()) {
            "android.newDsl=false"
        } else ""
    }
}
