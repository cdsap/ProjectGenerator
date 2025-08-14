import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    kotlin("jvm") version "2.2.10"
    id("org.jlleitschuh.gradle.ktlint") version "13.0.0"
}


project.extensions.getByType(KotlinJvmProjectExtension::class.java).apply {
    jvmToolchain(23)
}

