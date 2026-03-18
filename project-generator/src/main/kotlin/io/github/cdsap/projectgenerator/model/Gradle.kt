package io.github.cdsap.projectgenerator.model

enum class Gradle(val version: String) {
    GRADLE_8_14_3("8.14.3"),
    GRADLE_9_1_0("9.1.0"),
    GRADLE_9_2_0("9.2.0"),
    GRADLE_9_3_0("9.3.0"),
    GRADLE_9_3_1("9.3.1"),
    GRADLE_9_4_0("9.4.0");

    companion object {
        fun fromValue(value: String): Gradle {
            return entries.firstOrNull { it.version == value }
                ?: entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown Gradle version: $value")
        }
    }
}
