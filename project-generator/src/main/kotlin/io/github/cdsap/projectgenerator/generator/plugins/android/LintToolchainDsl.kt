package io.github.cdsap.projectgenerator.generator.plugins.android

import io.github.cdsap.projectgenerator.generator.extension.supportsLintToolchain
import io.github.cdsap.projectgenerator.model.Versions

internal fun lintToolchainImport(versions: Versions): String {
    if (!versions.android.agp.supportsLintToolchain()) {
        return ""
    }
    return "        |import org.gradle.jvm.toolchain.JavaLanguageVersion\n"
}

internal fun lintToolchainDsl(versions: Versions): String {
    if (!versions.android.agp.supportsLintToolchain()) {
        return ""
    }
    return """
                lint {
                    toolchain {
                        languageVersion.set(JavaLanguageVersion.of(${versions.project.jdk}))
                    }
                }
    """.trimStart('\n').trimEnd() + "\n"
}
