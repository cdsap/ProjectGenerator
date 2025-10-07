import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint)
}


project.extensions.getByType(KotlinJvmProjectExtension::class.java).apply {
    jvmToolchain(23)
}

