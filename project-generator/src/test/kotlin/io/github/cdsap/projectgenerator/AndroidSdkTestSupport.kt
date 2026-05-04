package io.github.cdsap.projectgenerator

import org.junit.jupiter.api.Assumptions.assumeTrue
import java.io.File

object AndroidSdkTestSupport {
    fun writeLocalProperties(projectDir: File) {
        val sdkDir = androidSdkDir()
        assumeTrue(sdkDir != null, "Android SDK not configured. Set ANDROID_HOME or ANDROID_SDK_ROOT.")

        File(projectDir, "local.properties").writeText("sdk.dir=${sdkDir!!.invariantSeparatorsPath}\n")
    }

    private fun androidSdkDir(): File? {
        return listOfNotNull(
            System.getenv("ANDROID_HOME"),
            System.getenv("ANDROID_SDK_ROOT"),
            "${System.getProperty("user.home")}/Library/Android/sdk"
        )
            .map(::File)
            .firstOrNull { it.isDirectory }
    }
}
