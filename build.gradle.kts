import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    kotlin("jvm") version "2.2.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
}


project.extensions.getByType(KotlinJvmProjectExtension::class.java).apply {
    jvmToolchain(23)
}

