package io.github.cdsap.projectgenerator.generator.plugins.android

import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class CompositeBuildPluginAndroidApp {
    fun get(versions: Versions) = """
        |package com.logic
        |
        |import org.gradle.api.Plugin
        |import org.gradle.api.JavaVersion
        |import org.gradle.api.Project
        |import org.gradle.kotlin.dsl.configure
        |import org.gradle.api.tasks.compile.JavaCompile
        |import org.gradle.jvm.toolchain.JavaLanguageVersion
        |import org.gradle.jvm.toolchain.JavaToolchainService
        |import org.gradle.kotlin.dsl.dependencies
        |import org.gradle.kotlin.dsl.withType
        |import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
        |
        |class CompositeBuildPluginAndroidApp : Plugin<Project> {
        |    override fun apply(target: Project) {
        |        with(target) {
        |            with(pluginManager) {
        |                apply("com.android.application")
        |                apply("org.jetbrains.kotlin.android")
        |                ${provideKotlinProcessor(versions)}
        |                apply("dagger.hilt.android.plugin")
        |                apply("org.jetbrains.kotlin.plugin.compose")
        |            }
        |
        |            extensions.configure<com.android.build.api.dsl.ApplicationExtension> {
        |                namespace = "com.awesome." + target.name.replace(":","_")
        |                compileSdk = 35
        |                defaultConfig {
        |                    applicationId = "com.awesome." + target.name.replace(":","_")
        |                    minSdk = 24
        |                    targetSdk = 35
        |                    versionCode = 1
        |                    versionName = "1.0"
        |                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        |                }
        |                buildTypes {
        |                    getByName("release") {
        |                        isMinifyEnabled = false
        |                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        |                    }
        |                }
        |                buildFeatures {
        |                    compose = true
        |                }
        |            }
        |            target.extensions.getByType(KotlinAndroidProjectExtension::class.java).apply {
        |                    jvmToolchain(${versions.project.jdk})
        |            }
        |            target.extensions.getByType(org.gradle.api.plugins.JavaPluginExtension::class.java).apply {
        |                toolchain.languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(${versions.project.jdk}))
        |            }
        |            // Hilt missing Java Toolchain support https://github.com/google/dagger/issues/4623
        |            val toolchains = target.extensions.getByType(JavaToolchainService::class.java)
        |            target.tasks.withType(JavaCompile::class.java)
        |                 .matching { it.name.startsWith("hiltJavaCompile") }
        |                 .configureEach {
        |                     javaCompiler.set(
        |                         toolchains.compilerFor {
        |                             languageVersion.set(JavaLanguageVersion.of(${versions.project.jdk}))
        |                         }
        |                     )
        |                 }
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
