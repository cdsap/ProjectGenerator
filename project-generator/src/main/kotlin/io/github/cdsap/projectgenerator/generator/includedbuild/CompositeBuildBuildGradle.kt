package io.github.cdsap.projectgenerator.generator.includedbuild

import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions

class CompositeBuildBuildGradle {

    fun get(versions: Versions, requested: TypeProjectRequested): String {
        val classpath = if (requested == TypeProjectRequested.ANDROID) {
            """
                implementation(libs.android.gradle.plugin)
                implementation(libs.hilt.plugin)
            """
        } else {
            ""
        }
        return  if (requested == TypeProjectRequested.ANDROID) {
            """
            |plugins {
            |    `kotlin-dsl`
            |}
            |
            |dependencies {
            |    implementation(libs.kotlin.plugin)
            |    implementation(libs.kotlin.compose.plugin)
            |
            |    $classpath
            |}
            |
            |gradlePlugin {
            |    plugins {
            |        register("androidLibPlugin") {
            |            id = "awesome.androidlib.plugin"
            |            implementationClass = "com.logic.CompositeBuildPluginAndroidLib"
            |        }
            |    }
            |}
            |gradlePlugin {
            |    plugins {
            |        register("androidAppPlugin") {
            |            id = "awesome.androidapp.plugin"
            |            implementationClass = "com.logic.CompositeBuildPluginAndroidApp"
            |        }
            |    }
            |}
            |
        """.trimMargin()
        }else{
            """
            |plugins {
            |    `kotlin-dsl`
            |}
            |
            |dependencies {
            |    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlin.kgp}")
            |}
            |
            |gradlePlugin {
            |    plugins {
            |        register("kotlinPlugin") {
            |            id = "awesome.kotlin.plugin"
            |            implementationClass = "com.logic.PluginJvmLib"
            |        }
            |    }
            |}
            |
        """.trimMargin()
        }
    }
}
