package io.github.cdsap.projectgenerator.generator.files

import io.github.cdsap.projectgenerator.generator.rootproject.GradleProperties
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Android
import io.github.cdsap.projectgenerator.model.Gradle
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
        val gradleProperties = GradleProperties().get(versions, Gradle("9.6.1"))
        Assertions.assertTrue(gradleProperties.contains("org.gradle.jvmargs"))
        Assertions.assertTrue(gradleProperties.contains("android.useAndroidX=true"))
        Assertions.assertTrue(gradleProperties.contains("org.gradle.caching=true"))
        Assertions.assertTrue(gradleProperties.contains("ksp.useKSP2=false"))
    }

    @Test
    fun `returns expected gradle properties for K2 enabled`() {
        val versions = Versions(
            kotlin = Kotlin(
                kgp = "2.1.20",
                ksp = "1.9.0",
                kotlinProcessor = KotlinProcessor(processor = Processor.KSP2)
            ),
            android = Android(agp = "8.0.0", hilt = "2.44"),
            project = Project(jdk = "17")
        )
        val gradleProperties = GradleProperties().get(versions, Gradle("9.6.1"))
        Assertions.assertTrue(gradleProperties.contains("org.gradle.jvmargs"))
        Assertions.assertTrue(gradleProperties.contains("android.useAndroidX=true"))
        Assertions.assertTrue(gradleProperties.contains("org.gradle.caching=true"))
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
        val gradleProperties = GradleProperties().get(versions, Gradle("9.6.1"))
        Assertions.assertTrue(!gradleProperties.contains("ksp.useKSP2=false"))
    }

    @Test
    fun `does not include android newDsl override for hilt on agp9`() {
        val versions = Versions(
            android = Android(agp = "9.1.0"),
            di = DependencyInjection.HILT
        )

        val gradleProperties = GradleProperties().get(versions, Gradle("9.6.1"))

        Assertions.assertFalse(gradleProperties.contains("android.newDsl"))
    }

    @Test
    fun `does not include android newDsl override for any di on agp9`() {
        DependencyInjection.entries.forEach { di ->
            val versions = Versions(
                android = Android(agp = "9.4.0"),
                di = di
            )

            val gradleProperties = GradleProperties().get(versions, Gradle("9.6.1"))

            Assertions.assertFalse(
                gradleProperties.contains("android.newDsl"),
                "Unexpected android.newDsl property for $di"
            )
        }
    }

    @Test
    fun `includes isolated projects properties for Gradle 9_7`() {
        val gradleProperties = GradleProperties().get(Versions(), Gradle("9.7.0"))

        Assertions.assertTrue(gradleProperties.contains("org.gradle.unsafe.isolated-projects=true"))
        Assertions.assertTrue(gradleProperties.contains("ksp.project.isolation.enabled=true"))
    }

    @Test
    fun `does not include isolated projects properties for Gradle before 9_7`() {
        val gradleProperties = GradleProperties().get(Versions(), Gradle("9.6.1"))

        Assertions.assertFalse(gradleProperties.contains("org.gradle.unsafe.isolated-projects=true"))
        Assertions.assertFalse(gradleProperties.contains("ksp.project.isolation.enabled=true"))
    }
}
