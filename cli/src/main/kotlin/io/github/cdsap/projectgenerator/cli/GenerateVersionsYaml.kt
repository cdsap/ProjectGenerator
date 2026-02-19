package io.github.cdsap.projectgenerator.cli

import io.github.cdsap.projectgenerator.model.AdditionalPlugin
import io.github.cdsap.projectgenerator.model.Versions
import java.io.File

class GenerateVersionsYaml {
    fun generate() {
        val file = File("versions.yaml")
        val versions = Versions()
        val content = """
            |project:
            |  develocity: ${versions.project.develocity}
            |  develocityUrl: ${versions.project.develocityUrl}
            |  jdk: ${versions.project.jdk}
            |di: ${versions.di}
            |kotlin:
            |  kgp: ${versions.kotlin.kgp}
            |  ksp: ${versions.kotlin.ksp}
            |  coroutines: ${versions.kotlin.coroutines}
            |  kotlinProcessor:
            |    processor: ${versions.kotlin.kotlinProcessor.processor}
            |android:
            |  agp: ${versions.android.agp}
            |  androidxCore: ${versions.android.androidxCore}
            |  appcompat: ${versions.android.appcompat}
            |  material: ${versions.android.material}
            |  lifecycle: ${versions.android.lifecycle}
            |  fragment: ${versions.android.fragment}
            |  activity: ${versions.android.activity}
            |  constraintlayout: ${versions.android.constraintlayout}
            |  work: ${versions.android.work}
            |  room: ${versions.android.room}
            |  roomDatabase: ${versions.android.roomDatabase}
            |  kotlinMultiplatformLibrary: ${versions.android.kotlinMultiplatformLibrary}
            |  hilt: ${versions.android.hilt}
            |  hiltAandroidx: ${versions.android.hiltAandroidx}
            |  metro: ${versions.android.metro}
            |  metroPlugin: ${versions.android.metroPlugin}
            |  composeBom: ${versions.android.composeBom}
            |testing:
            |  junit4: ${versions.testing.junit4}
            |  junit5: ${versions.testing.junit5}
            |  truth: ${versions.testing.truth}
            |  mockk: ${versions.testing.mockk}
            |  coreTesting: ${versions.testing.coreTesting}
            |  junitExt: ${versions.testing.junitExt}
            |additionalSettingsPlugins:
            |  ${additionalPlugins(versions.additionalSettingsPlugins)}
            |additionalBuildGradleRootPlugins:
            |  ${additionalPlugins(versions.additionalBuildGradleRootPlugins)}
        """.trimMargin()
        file.writeText(content)
        if (file.exists()) println("file versions.yaml created ")
    }

    fun additionalPlugins(plugins: List<AdditionalPlugin>): String {
        return plugins.joinToString("\n") { plugin ->
            "  - id: ${plugin.id}\n" +
                "      version: ${plugin.version}\n" +
                "      apply: ${plugin.apply}"
        }
    }
}
