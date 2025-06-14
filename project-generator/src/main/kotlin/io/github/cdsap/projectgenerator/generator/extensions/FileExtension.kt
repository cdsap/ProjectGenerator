package io.github.cdsap.projectgenerator.generator.extensions

import java.io.File

fun File.projectFile(content: String) {
    createNewFile()
    writeText(content)
}
