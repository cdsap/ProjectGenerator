package io.github.cdsap.projectgenerator.generator.toml

import io.github.cdsap.projectgenerator.model.Processor
import io.github.cdsap.projectgenerator.model.Versions

class AndroidToml {

    fun toml(version: Versions) = """
        [versions]
        androidx-core = "${version.android.androidxCore}"
        appcompat = "${version.android.appcompat}"
        material = "${version.android.material}"
        lifecycle = "${version.android.lifecycle}"
        coroutines = "${version.kotlin.coroutines}"
        fragment = "${version.android.fragment}"
        activity = "${version.android.activity}"
        constraintlayout = "${version.android.constraintlayout}"
        work = "${version.android.work}"
        hilt = "${version.android.hilt}"
        hilt-androidx = "${version.android.hiltAandroidx}"
        compose-bom = "${version.android.composeBom}"
        junit4 = "${version.testing.junit4}"
        junit5 = "${version.testing.junit5}"
        truth = "${version.testing.truth}"
        mockk = "${version.testing.mockk}"
        kotlin-test = "${version.kotlin.kotlinTest}"
        core-testing = "${version.testing.coreTesting}"
        robolectric = "${version.android.robolectric}"
        espresso = "${version.android.espresso}"
        junit-ext = "${version.testing.junitExt}"

        [libraries]
        androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "androidx-core" }
        appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
        material = { group = "com.google.android.material", name = "material", version.ref = "material" }
        lifecycle-viewmodel-ktx = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-ktx", version.ref = "lifecycle" }
        lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycle" }
        lifecycle-livedata-ktx = { group = "androidx.lifecycle", name = "lifecycle-livedata-ktx", version.ref = "lifecycle" }
        lifecycle-common-java8 = { group = "androidx.lifecycle", name = "lifecycle-common-java8", version.ref = "lifecycle" }
        coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }
        fragment-ktx = { group = "androidx.fragment", name = "fragment-ktx", version.ref = "fragment" }
        activity-ktx = { group = "androidx.activity", name = "activity-ktx", version.ref = "activity" }
        constraintlayout = { group = "androidx.constraintlayout", name = "constraintlayout", version.ref = "constraintlayout" }
        work-runtime-ktx = { group = "androidx.work", name = "work-runtime-ktx", version.ref = "work" }
        hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
        hilt-work = { group = "androidx.hilt", name = "hilt-work", version.ref = "hilt-androidx" }
        hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }
        hilt-android-testing = { group = "com.google.dagger", name = "hilt-android-testing", version.ref = "hilt" }
        hilt-compiler-androidx = { group = "androidx.hilt", name = "hilt-compiler", version.ref = "hilt-androidx" }
        hilt-compiler-android-androidx = { group = "androidx.hilt", name = "hilt-android", version.ref = "hilt-androidx" }

        compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "compose-bom" }
        compose-ui = { group = "androidx.compose.ui", name = "ui" }
        compose-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
        compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
        compose-material3 = { group = "androidx.compose.material3", name = "material3" }
        compose-runtime = { group = "androidx.compose.runtime", name = "runtime" }
        compose-runtime-livedata = { group = "androidx.compose.runtime", name = "runtime-livedata" }
        activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activity" }
        lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycle" }
        compose-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
        compose-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }

        junit4 = { group = "junit", name = "junit", version.ref = "junit4" }
        junit5-vintage = { group = "org.junit.vintage", name = "junit-vintage-engine", version.ref = "junit5" }
        coroutines-test = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-test", version.ref = "coroutines" }
        core-testing = { group = "androidx.arch.core", name = "core-testing", version.ref = "core-testing" }
        mockk = { group = "io.mockk", name = "mockk", version.ref = "mockk" }
        truth = { group = "com.google.truth", name = "truth", version.ref = "truth" }
        kotlin-test = { group = "org.jetbrains.kotlin", name = "kotlin-test", version.ref = "kotlin-test" }
        androidx-test-core = { group = "androidx.test", name = "core", version = "1.5.0" }
        work-testing = { group = "androidx.work", name = "work-testing", version.ref = "work" }
        robolectric = { group = "org.robolectric", name = "robolectric", version.ref = "robolectric" }
        androidx-test-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junit-ext" }
        espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espresso" }
    """.trimIndent()

    fun tomlImplementations(version: Versions) = """
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.coroutines.android)
    implementation(libs.fragment.ktx)
    implementation(libs.activity.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)
    implementation(libs.hilt.android)

    ${kotlinProcessor(version)}(libs.hilt.compiler.androidx)
    ${kotlinProcessor(version)}(libs.hilt.compiler)
    ${kotlinProcessor(version)}Test(libs.hilt.compiler)
    ${kotlinProcessor(version)}AndroidTest(libs.hilt.compiler)
    testImplementation(libs.hilt.android.testing)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    testImplementation(libs.junit4)
    testImplementation(libs.junit5.vintage)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.core.testing)
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.work.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.espresso.core)
""".trimIndent()

    fun kotlinProcessor(version: Versions): String {
        if (version.kotlin.kotlinProcessor.processor == Processor.KAPT) {
            return "kapt"
        } else {
            return "ksp"
        }
    }
}
