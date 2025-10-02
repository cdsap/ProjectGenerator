package io.github.cdsap.projectgenerator.generator.plugins.android

import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class CompositeBuildPluginAndroidLib {
    fun get(versions: Versions) = """
        |package com.logic
        |
        |import org.gradle.api.Plugin
        |import org.gradle.api.JavaVersion
        |import org.gradle.api.Project
        |import org.gradle.kotlin.dsl.configure
        |import org.gradle.kotlin.dsl.dependencies
        |import org.gradle.kotlin.dsl.withType
        |import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
        |
        |class CompositeBuildPluginAndroidLib : Plugin<Project> {
        |    override fun apply(target: Project) {
        |        with(target) {
        |            with(pluginManager) {
        |                apply("com.android.library")
        |                apply("org.jetbrains.kotlin.android")
        |                ${provideKotlinProcessor(versions)}
        |                apply("dagger.hilt.android.plugin")
        |                apply("org.jetbrains.kotlin.plugin.compose")
        |            }
        |
        |            extensions.configure<com.android.build.gradle.LibraryExtension>  {
        |                namespace = "com.awesome." + target.name.replace(":","_").replace("-", "")
        |                compileSdk = 36
        |                defaultConfig {
        |                    minSdk = 24
        |                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        |                }
        |                buildTypes {
        |                    getByName("release") {
        |                        isMinifyEnabled = false
        |                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        |                    }
        |                }
        |                compileOptions {
        |                    sourceCompatibility = JavaVersion.VERSION_${versions.project.jdk}
        |                    targetCompatibility = JavaVersion.VERSION_${versions.project.jdk}
        |                }
        |                buildFeatures {
        |                    compose = true
        |                }
        |            }
        |            target.extensions.getByType(KotlinAndroidProjectExtension::class.java).apply {
        |                    jvmToolchain(${versions.project.jdk})
        |            }
        |
        |            target.extensions.getByType(org.gradle.api.plugins.JavaPluginExtension::class.java).apply {
        |                toolchain.languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(${versions.project.jdk}))
        |            }
        |
        |            dependencies {
        |
        |            }
        |        }
        |    }
        |}
        |""".trimMargin()

    fun provideKotlinProcessor(versions: Versions) = if (versions.kotlin.kotlinProcessor.processor == Processor.KAPT)
        """apply("kotlin-kapt")"""
    else
        """apply("com.google.devtools.ksp")"""
}
