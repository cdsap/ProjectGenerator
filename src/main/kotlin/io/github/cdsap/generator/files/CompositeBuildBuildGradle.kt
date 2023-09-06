package io.github.cdsap.generator.files

class CompositeBuildBuildGradle {

    fun get() =  """
            plugins {
                `kotlin-dsl`
            }

            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
            }

            gradlePlugin {
                plugins {
                    register("kotlinPlugin") {
                        id = "awesome.kotlin.plugin"
                        implementationClass = "com.logic.Plugin1"
                    }
                }
            }

        """.trimIndent()
}
