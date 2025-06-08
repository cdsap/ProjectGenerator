package io.github.cdsap.projectgenerator.files

import java.io.File

fun File.projectFile(content: String) {
    createNewFile()
    writeText(content)
}
