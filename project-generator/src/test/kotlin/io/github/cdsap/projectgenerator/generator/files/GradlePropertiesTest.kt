package io.github.cdsap.projectgenerator.generator.files

import io.github.cdsap.projectgenerator.files.GradleProperties
import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.Kotlin
import io.github.cdsap.projectgenerator.model.KotlinProcessor
import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Project
import io.github.cdsap.projectgenerator.model.Versions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GradlePropertiesTest {
    @Test
    fun `returns expected gradle properties for KSP processor`() {
        val versions = Versions(
            kotlin = Kotlin(
                kgp = "2.1.20",
                ksp = "1.9.0",
                kotlinProcessor = KotlinProcessor(processor = Processor.KSP)
            ),
            android = Android(agp = "8.0.0", hilt = "2.44"),
            project = Project(jdk = "17")
        )
        val gradleProperties = GradleProperties().get(versions)
        Assertions.assertTrue(gradleProperties.contains("org.gradle.jvmargs"))
        Assertions.assertTrue(gradleProperties.contains("android.useAndroidX=true"))
        Assertions.assertTrue(gradleProperties.contains("org.gradle.cache=true"))
        Assertions.assertTrue(gradleProperties.contains("ksp.useKSP2=false"))
    }

    @Test
    fun `returns expected gradle properties for K2 enabled`() {
        val versions = Versions(
            kotlin = Kotlin(
                kgp = "2.1.20",
                ksp = "1.9.0",
                kotlinProcessor = KotlinProcessor(processor = Processor.KSP_K2)
            ),
            android = Android(agp = "8.0.0", hilt = "2.44"),
            project = Project(jdk = "17")
        )
        val gradleProperties = GradleProperties().get(versions)
        Assertions.assertTrue(gradleProperties.contains("org.gradle.jvmargs"))
        Assertions.assertTrue(gradleProperties.contains("android.useAndroidX=true"))
        Assertions.assertTrue(gradleProperties.contains("org.gradle.cache=true"))
        Assertions.assertTrue(!gradleProperties.contains("ksp.useKSP2=false"))
    }

    @Test
    fun `does not include ksp property for non-KSP processor`() {
        val versions = Versions(
            kotlin = Kotlin(
                kgp = "1.9.0",
                ksp = "1.9.0",
                kotlinProcessor = KotlinProcessor(processor = Processor.KAPT)
            ),
            android = Android(agp = "8.0.0", hilt = "2.44"),
            project = Project(jdk = "17")
        )
        val gradleProperties = GradleProperties().get(versions)
        Assertions.assertTrue(!gradleProperties.contains("ksp.useKSP2=false"))
    }
}
