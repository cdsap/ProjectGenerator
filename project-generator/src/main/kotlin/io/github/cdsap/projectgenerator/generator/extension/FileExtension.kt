package io.github.cdsap.projectgenerator.generator.extension

import java.io.File

fun File.projectFile(content: String) {
    createNewFile()
    writeText(content)
}

fun String.isAgp9(): Boolean {
    return this.startsWith("9.")
}

fun String.supportsLintToolchain(): Boolean {
    val parts = split('.')
    val major = parts.getOrNull(0)?.toIntOrNull() ?: return false
    val minor = parts.getOrNull(1)?.toIntOrNull() ?: return false
    return major > 9 || (major == 9 && minor >= 5)
}
