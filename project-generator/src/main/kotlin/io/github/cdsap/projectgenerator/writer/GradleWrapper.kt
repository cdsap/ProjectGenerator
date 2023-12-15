package io.github.cdsap.projectgenerator.writer

import io.github.cdsap.projectgenerator.model.Gradle
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

class GradleWrapper(val gradle: Gradle) {

    fun installGradleVersion(path: String) {
        val zipFileName = "${gradle.name.lowercase()}.zip"
        val outputDir = path
        unzipResourceFile(zipFileName, outputDir)

    }

    fun unzipResourceFile(zipFileName: String, outputDir: String) {
        // Access the file from the resources folder
        val resourceStream = this::class.java.classLoader.getResourceAsStream(zipFileName)
            ?: throw IllegalArgumentException("File not found: $zipFileName")

        ZipInputStream(resourceStream).use { zip ->
            var entry = zip.nextEntry
            while (entry != null) {
                val filePath = "$outputDir/${entry.name}"
                if (!entry.isDirectory) {
                    extractFile(zip, filePath)
                } else {
                    val dir = File(filePath)
                    dir.mkdir()
                }
                zip.closeEntry()
                entry = zip.nextEntry
            }
        }
    }

    private fun extractFile(zipIn: ZipInputStream, filePath: String) {
        FileOutputStream(filePath).use { fos ->
            val buffer = ByteArray(4096)
            var length: Int
            while (zipIn.read(buffer).also { length = it } != -1) {
                fos.write(buffer, 0, length)
            }
        }
        File(filePath).setExecutable(true)
    }
}
