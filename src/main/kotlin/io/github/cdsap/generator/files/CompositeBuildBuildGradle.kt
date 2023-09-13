package io.github.cdsap.generator.files

import io.github.cdsap.generator.model.Versions

class CompositeBuildBuildGradle {

    fun get(versions: Versions) = """
            plugins {
                `kotlin-dsl`
            }

            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kgp}")
                implementation("com.android.tools.build:gradle:${versions.agp}")
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
