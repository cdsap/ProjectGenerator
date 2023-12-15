package io.github.cdsap.projectgenerator.files

import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions

class CompositeBuildBuildGradle {

    fun get(versions: Versions, requested: TypeProjectRequested): String {
        val classpath = if (requested == TypeProjectRequested.ANDROID) {
            """
                implementation("com.android.tools.build:gradle:${versions.agp}")
            """.trimIndent()
        } else {
            ""
        }
        return """
            plugins {
                `kotlin-dsl`
            }

            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kgp}")
                $classpath
            }

            gradlePlugin {
                plugins {
                    register("kotlinPlugin") {
                        id = "awesome.kotlin.plugin"
                        implementationClass = "com.logic.Plugin1"
                    }
                }
            }
            gradlePlugin {
                plugins {
                    register("androidLibPlugin") {
                        id = "awesome.androidlib.plugin"
                        implementationClass = "com.logic.CompositeBuildPluginAndroidLib"
                    }
                }
            }
            gradlePlugin {
                plugins {
                    register("androidAppPlugin") {
                        id = "awesome.androidapp.plugin"
                        implementationClass = "com.logic.CompositeBuildPluginAndroidApp"
                    }
                }
            }

        """.trimIndent()
    }
}
