package io.github.cdsap.projectgenerator.model

import kotlin.String

data class Versions(
    val kotlin: Kotlin = Kotlin(),
    val android: Android = Android(),
    val testing: Testing = Testing(),
    val project: Project = Project(),
    val additionalBuildGradleRootPlugins: List<AdditionalPlugin> = listOf(
        AdditionalPlugin(
            "com.autonomousapps.dependency-analysis",
            "2.19.0"
        )
    ),
    val additionalSettingsPlugins: List<AdditionalPlugin> = listOf(
        AdditionalPlugin(
            "com.fueledbycaffeine.spotlight",
            "1.3.3"
        )
    )
)

enum class Processor {
    KSP,
    KSP_K2,
    KAPT,
}

data class AdditionalPlugin(
    val id: String,
    val version: String,
    val apply: Boolean = true
)


data class Project(
    val jdk: String = "23",
    val develocity: String = "4.1.1",
    val ccud: String = "2.2",
    val develocityUrl: String = ""
)

data class KotlinProcessor(
    val processor: Processor = Processor.KSP
)

data class Testing(
    val junit4: String = "4.13.2",
    val junit5: String = "5.13.4",
    val truth: String = "1.4.5",
    val mockk: String = "1.14.5",
    val coreTesting: String = "2.2.0",
    val junitExt: String = "1.3.0",
)

data class Kotlin(
    val kgp: String = "2.2.20",
    val ksp: String = "2.2.20-2.0.3",
    val coroutines: String = "1.10.2",
    val kotlinTest: String = "2.2.20",
    val kotlinProcessor: KotlinProcessor = KotlinProcessor(),
)

data class Android(
    val agp: String = "8.13.0",
    val androidxCore: String = "1.16.0",
    val appcompat: String = "1.7.1",
    val material: String = "1.13.0",
    val lifecycle: String = "2.9.3",
    val fragment: String = "1.8.9",
    val activity: String = "1.10.1",
    val constraintlayout: String = "2.2.1",
    val work: String = "2.10.4",
    val hilt: String = "2.57.1",
    val hiltAandroidx: String = "1.3.0",
    val composeBom: String = "2025.08.01",
    val robolectric: String = "4.15.1",
    val espresso: String = "3.7.0",
)
