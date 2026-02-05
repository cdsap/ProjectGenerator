package io.github.cdsap.projectgenerator.generator.plugins.android

import io.github.cdsap.projectgenerator.generator.extension.isAgp9
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class CompositeBuildPluginAndroidApp {
    fun get(versions: Versions, di: DependencyInjection) = """
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
        |                ${provideKgpBasedOnAgp(versions)}
        |                ${provideKotlinProcessor(versions, di)}
        |                ${applyDiPlugin(di)}
        |                apply("org.jetbrains.kotlin.plugin.compose")
        |            }
        |
        |            extensions.configure<com.android.build.api.dsl.ApplicationExtension> {
        |                namespace = "com.awesome." + target.name.replace(":","_").replace("-", "")
        |                compileSdk = 36
        |                defaultConfig {
        |                    applicationId = "com.awesome." + target.name.replace(":","_").replace("-", "")
        |                    minSdk = 24
        |                    targetSdk = 36
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
        |            ${hiltToolchainFix(versions, di)}
        |
        |            dependencies {
        |
        |            }
        |        }
        |    }
        |}
        |""".trimMargin()

    fun provideKotlinProcessor(versions: Versions, di: DependencyInjection) = if (versions.kotlin.kotlinProcessor.processor == Processor.KAPT)
        """apply("kotlin-kapt")"""
    else if( di == DependencyInjection.HILT)
        """apply("com.google.devtools.ksp")"""
    else
        ""

    fun provideKgpBasedOnAgp(versions: Versions) = if (!versions.android.agp.isAgp9())
        """apply("org.jetbrains.kotlin.android")"""
    else
        """"""

    fun applyDiPlugin(di: DependencyInjection): String {
        return when (di) {
            DependencyInjection.HILT -> """apply("dagger.hilt.android.plugin")"""
            DependencyInjection.METRO -> """apply("dev.zacsweers.metro")"""
            DependencyInjection.NONE -> """"""
        }
    }

    fun hiltToolchainFix(versions: Versions, di: DependencyInjection): String {
        return if (di == DependencyInjection.HILT) {
            """
            // Hilt missing Java Toolchain support https://github.com/google/dagger/issues/4623
            val toolchains = target.extensions.getByType(JavaToolchainService::class.java)
            target.tasks.withType(JavaCompile::class.java)
                 .matching { it.name.startsWith("hiltJavaCompile") }
                 .configureEach {
                     javaCompiler.set(
                         toolchains.compilerFor {
                             languageVersion.set(JavaLanguageVersion.of(${versions.project.jdk}))
                         }
                     )
                 }
            """.trimIndent()
        } else {
            ""
        }
    }
}
