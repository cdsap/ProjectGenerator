package io.github.cdsap.projectgenerator.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

data class Gradle @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
    @get:JsonValue val version: String
) {

    val cliValue: String
        get() = "gradle_${version.replace('.', '_')}"

    val legacyEnumName: String
        get() = cliValue.uppercase()

    val resourceZipName: String
        get() = "${cliValue.lowercase()}.zip"

    companion object {
        private const val SUPPORTED_VERSIONS_RESOURCE = "supported-gradle-versions.txt"

        private val supportedVersions: List<Gradle> by lazy {
            val stream = requireNotNull(Gradle::class.java.classLoader.getResourceAsStream(SUPPORTED_VERSIONS_RESOURCE)) {
                "Missing resource: $SUPPORTED_VERSIONS_RESOURCE"
            }
            stream.bufferedReader().useLines { lines ->
                lines
                    .map(String::trim)
                    .filter { it.isNotEmpty() && !it.startsWith("#") }
                    .map(::Gradle)
                    .toList()
            }
        }

        private val supportedLookup: Map<String, Gradle> by lazy {
            supportedVersions.flatMap { gradle ->
                listOf(
                    gradle.version.lowercase() to gradle,
                    gradle.cliValue.lowercase() to gradle,
                    gradle.legacyEnumName.lowercase() to gradle
                )
            }.toMap()
        }

        fun supported(): List<Gradle> = supportedVersions

        fun latest(): Gradle = supported().first()

        fun oldest(): Gradle = supported().last()

        fun supportedCliChoices(): List<String> = supported().flatMap { listOf(it.cliValue, it.version) }

        fun supportedDisplayValues(): String = supported().joinToString(", ") { "${it.cliValue} (${it.version})" }

        fun isSupportedValue(value: String): Boolean = supportedLookup.containsKey(value.lowercase())

        fun fromValue(value: String): Gradle {
            return supportedLookup[value.lowercase()]
                ?: throw IllegalArgumentException("Unknown Gradle version: $value")
        }
    }
}
