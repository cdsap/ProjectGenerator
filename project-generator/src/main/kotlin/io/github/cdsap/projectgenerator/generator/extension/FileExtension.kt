package io.github.cdsap.projectgenerator.generator.extension

import java.io.File

fun File.projectFile(content: String) {
    createNewFile()
    writeText(content)
}

fun String.isAgp9(): Boolean {
    return this.startsWith("9.")
}
