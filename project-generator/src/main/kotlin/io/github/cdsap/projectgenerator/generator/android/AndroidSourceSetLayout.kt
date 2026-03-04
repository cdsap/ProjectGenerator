package io.github.cdsap.projectgenerator.generator.android

import io.github.cdsap.projectgenerator.model.TypeProject

object AndroidSourceSetLayout {
    fun kotlinMainSourceDir(type: TypeProject, kotlinMultiplatformLibrary: Boolean): String {
        return if (kotlinMultiplatformLibrary && type == TypeProject.ANDROID_LIB) {
            "src/androidMain/kotlin"
        } else {
            "src/main/kotlin"
        }
    }

    fun kotlinTestSourceDir(type: TypeProject, kotlinMultiplatformLibrary: Boolean): String {
        return if (kotlinMultiplatformLibrary && type == TypeProject.ANDROID_LIB) {
            "src/androidHostTest/kotlin"
        } else {
            "src/test/kotlin"
        }
    }

    fun resourcesSourceDir(type: TypeProject, kotlinMultiplatformLibrary: Boolean): String {
        return if (kotlinMultiplatformLibrary && type == TypeProject.ANDROID_LIB) {
            "src/androidMain/res"
        } else {
            "src/main/res"
        }
    }

    fun manifestSourceDir(type: TypeProject, kotlinMultiplatformLibrary: Boolean): String {
        return if (kotlinMultiplatformLibrary && type == TypeProject.ANDROID_LIB) {
            "src/androidMain"
        } else {
            "src/main"
        }
    }
}
