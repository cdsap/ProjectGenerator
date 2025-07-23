package io.github.cdsap.projectgenerator.generator.rootproject

import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class GradleProperties {
    fun get(versions: Versions) = """
        org.gradle.jvmargs=-Xmx5g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -XX:+UseParallelGC -XX:MaxMetaspaceSize=1g
        android.useAndroidX=true
        org.gradle.caching=true
        ${k2usage(versions)}
    """.trimIndent()

    // Disable K2 for KSP 2.0
    private fun k2usage(versions: Versions): String {
        return if (versions.kotlin.kotlinProcessor.processor == Processor.KSP) {
            "ksp.useKSP2=false"
        } else ""
    }
}
