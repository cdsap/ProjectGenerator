package io.github.cdsap.generator.files

class CompositeBuildPlugin1 {
    fun get() = """
            package com.logic

            import org.gradle.api.Plugin
            import org.gradle.api.JavaVersion
            import org.gradle.api.Project
            import org.gradle.kotlin.dsl.withType

            class Plugin1 : Plugin<Project> {
                override fun apply(target: Project) {
                    with(target) {
                        with(pluginManager) {
                            apply("org.jetbrains.kotlin.jvm")
                        }
                        dependencies {
                            add("testImplementation","junit:junit:4.13.2")
                            add("testImplementation","org.junit.vintage:junit-vintage-engine:5.10.1")
                        }
                    }

                    target.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
                        kotlinOptions {
                            jvmTarget = JavaVersion.VERSION_17.toString()
                        }
                    }

                }
            }

        """.trimIndent()
}
