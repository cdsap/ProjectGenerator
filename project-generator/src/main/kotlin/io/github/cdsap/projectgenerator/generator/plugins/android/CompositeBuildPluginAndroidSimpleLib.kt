package io.github.cdsap.projectgenerator.generator.plugins.android

class CompositeBuildPluginAndroidSimpleLib {
    fun get() = """
            package com.logic

            import org.gradle.api.Plugin
            import org.gradle.api.JavaVersion
            import org.gradle.api.Project
            import org.gradle.kotlin.dsl.configure
            import org.gradle.kotlin.dsl.dependencies
            import org.gradle.kotlin.dsl.withType

            class CompositeBuildPluginAndroidLib : Plugin<Project> {
                override fun apply(target: Project) {
                    with(target) {
                        with(pluginManager) {
                            apply("com.android.library")
                            apply("org.jetbrains.kotlin.android")
                            apply("kotlin-kapt")
                        }

                     val name = target.name.replace(":","_").replace("-", "")
                     extensions.configure<com.android.build.gradle.LibraryExtension>  {
                        namespace = "com.example.mylibrary.${'$'}name"
                        compileSdk = 33

                        defaultConfig {
                            minSdk = 24
                            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                        }

                        compileOptions {
                            sourceCompatibility = JavaVersion.VERSION_11
                            targetCompatibility = JavaVersion.VERSION_11
                        }

                    }
                     tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
                        kotlinOptions {
                            jvmTarget = JavaVersion.VERSION_11.toString()
                        }
                    }

                    dependencies {

                        add("implementation","androidx.core:core-ktx:1.9.0")
                        add("implementation","androidx.appcompat:appcompat:1.6.1")
                        add("implementation","com.google.android.material:material:1.8.0")
                        add("testImplementation","junit:junit:4.13.2")
                        add("testImplementation","org.junit.vintage:junit-vintage-engine:5.10.1")
                        add("androidTestImplementation","androidx.test.ext:junit:1.1.5")
                        add("androidTestImplementation","androidx.test.espresso:espresso-core:3.5.1")
                    }
}
                }
            }

        """.trimIndent()
}
