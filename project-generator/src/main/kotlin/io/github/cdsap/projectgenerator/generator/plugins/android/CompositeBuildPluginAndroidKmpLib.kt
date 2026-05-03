package io.github.cdsap.projectgenerator.generator.plugins.android

import io.github.cdsap.projectgenerator.generator.extension.isAgp9
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class CompositeBuildPluginAndroidKmpLib {
    fun get(versions: Versions, di: DependencyInjection) = """
        |package com.logic
        |
        |import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
        |import org.gradle.api.Plugin
        |import org.gradle.api.Project
        |import org.gradle.kotlin.dsl.configure
        |import org.gradle.kotlin.dsl.dependencies
        |import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
        |
        |class CompositeBuildPluginAndroidKmpLib : Plugin<Project> {
        |    override fun apply(target: Project) {
        |        with(target) {
        |            with(pluginManager) {
        |                apply("com.android.kotlin.multiplatform.library")
        |                apply("org.jetbrains.kotlin.multiplatform")
        |                ${provideKotlinProcessor(versions, di)}
        |                ${applyDiPlugin(di)}
        |                apply("org.jetbrains.kotlin.plugin.compose")
        |            }
        |
        |            extensions.configure<KotlinMultiplatformExtension> {
        |                targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
        |                    namespace = "com.awesomeapp." + target.name.replace(":","_").replace("-", "")
        |                    compileSdk = 36
        |                    minSdk = 24
        |                    withHostTestBuilder {}
        |                    withDeviceTestBuilder {
        |                        sourceSetTreeName = "test"
        |                    }.configure {
        |                        instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        |                    }
        |                    withJava()
        |                    androidResources.enable = true
        |                }
        |
        |                jvmToolchain(${versions.project.jdk})
        |            }
        |
        |            dependencies {
        |
        |            }
        |
        |            ${wireKspAndroidMainForReleaseChecks(versions, di)}
        |        }
        |    }
        |}
        |""".trimMargin()

    private fun provideKotlinProcessor(versions: Versions, di: DependencyInjection): String {
        if (versions.kotlin.kotlinProcessor.processor == Processor.KAPT) {
            return if (versions.android.agp.isAgp9()) {
                """apply("com.android.legacy-kapt")"""
            } else {
                """apply("kotlin-kapt")"""
            }
        }
        val shouldApplyKsp = di == DependencyInjection.HILT || versions.android.roomDatabase
        return if (shouldApplyKsp) """apply("com.google.devtools.ksp")""" else ""
    }

    private fun applyDiPlugin(di: DependencyInjection): String {
        return when (di) {
            DependencyInjection.HILT -> ""
            DependencyInjection.METRO -> """apply("dev.zacsweers.metro")"""
            DependencyInjection.NONE -> ""
        }
    }

    private fun wireKspAndroidMainForReleaseChecks(versions: Versions, di: DependencyInjection): String {
        val shouldApplyKsp = versions.kotlin.kotlinProcessor.processor != Processor.KAPT &&
            (di == DependencyInjection.HILT || versions.android.roomDatabase)
        return if (shouldApplyKsp) {
            """
            tasks.matching { it.name == "extractAndroidMainAnnotations" }.configureEach {
                dependsOn("kspAndroidMain")
            }
            """.trimIndent()
        } else {
            ""
        }
    }
}
