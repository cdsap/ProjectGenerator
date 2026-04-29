package io.github.cdsap.projectgenerator

import java.util.Properties

object ProjectGeneratorVersion {
    val value: String by lazy {
        Properties().apply {
            ProjectGeneratorVersion::class.java
                .getResourceAsStream("/project-generator-version.properties")
                ?.use(::load)
        }.getProperty("version", "unspecified")
    }
}
