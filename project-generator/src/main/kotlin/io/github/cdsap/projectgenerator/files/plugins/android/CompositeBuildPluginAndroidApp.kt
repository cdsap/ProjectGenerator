package io.github.cdsap.projectgenerator.files.plugins.android

import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class CompositeBuildPluginAndroidApp {
    fun get(versions: Versions) = """
            package com.logic

            import org.gradle.api.Plugin
            import org.gradle.api.JavaVersion
            import org.gradle.api.Project
            import org.gradle.kotlin.dsl.configure
            import org.gradle.kotlin.dsl.dependencies
            import org.gradle.kotlin.dsl.withType

            class CompositeBuildPluginAndroidApp : Plugin<Project> {
                override fun apply(target: Project) {
                    with(target) {
                        with(pluginManager) {
                            apply("com.android.application")
                            apply("org.jetbrains.kotlin.android")
                            ${provideKotlinProcessor(versions)}
                            apply("dagger.hilt.android.plugin")
                            apply("org.jetbrains.kotlin.plugin.compose")
                        }

                        val moduleName = target.name.replace(":","_")
                        extensions.configure<com.android.build.api.dsl.ApplicationExtension> {
                            namespace = "com.awesome." + target.name.replace(":","_")
                            compileSdk = 35
                            defaultConfig {
                                applicationId = "com.awesome." + target.name.replace(":","_")
                                minSdk = 24
                                targetSdk = 35
                                versionCode = 1
                                versionName = "1.0"
                                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                            }
                            buildTypes {
                                getByName("release") {
                                    isMinifyEnabled = false
                                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                                }
                            }
                            compileOptions {
                                sourceCompatibility = JavaVersion.VERSION_${versions.project.jdk}
                                targetCompatibility = JavaVersion.VERSION_${versions.project.jdk}
                            }
                            buildFeatures {
                                compose = true
                            }
                        }
                        target.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
                            kotlinOptions {
                                jvmTarget = JavaVersion.VERSION_${versions.project.jdk}.toString()
                            }
                        }


                        dependencies {

                        }
                    }
                }
            }

        """.trimIndent()

    fun provideKotlinProcessor(versions: Versions) = if (versions.kotlin.kotlinProcessor.processor == Processor.KAPT)
        """apply("kotlin-kapt")"""
    else
        """apply("com.google.devtools.ksp")"""

}
