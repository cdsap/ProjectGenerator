package io.github.cdsap.projectgenerator.generator.plugins.jvm

import io.github.cdsap.projectgenerator.model.Versions

class CompositeBuildJvmLib {
    fun get(versions: Versions) = """
        |package com.logic
        |
        |import org.gradle.api.Plugin
        |import org.gradle.api.JavaVersion
        |import org.gradle.api.Project
        |import org.gradle.kotlin.dsl.dependencies
        |import org.gradle.kotlin.dsl.withType
        |import org.gradle.kotlin.dsl.withType
        |import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
        |
        |class PluginJvmLib : Plugin<Project> {
        |    override fun apply(target: Project) {
        |        with(target) {
        |            with(pluginManager) {
        |                apply("org.jetbrains.kotlin.jvm")
        |            }
        |            dependencies {
        |                add("implementation","org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
        |                add("testImplementation","junit:junit:4.13.2")
        |                add("testImplementation","org.jetbrains.kotlin:kotlin-test:2.1.20")
        |                add("testImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
        |                add("testImplementation","org.junit.vintage:junit-vintage-engine:5.10.1")
        |            }
        |        }
        |        target.extensions.getByType(KotlinJvmProjectExtension::class.java).apply {
        |            jvmToolchain(${versions.project.jdk})
        |        }
        |        target.extensions.getByType(org.gradle.api.plugins.JavaPluginExtension::class.java).apply {
        |           toolchain.languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(${versions.project.jdk}))
        |        }
        |
        |    }
        |}
        |""".trimMargin()
}
