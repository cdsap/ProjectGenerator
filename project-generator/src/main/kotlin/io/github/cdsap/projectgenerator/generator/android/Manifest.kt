package io.github.cdsap.projectgenerator.generator.android

import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryAndroid
import io.github.cdsap.projectgenerator.model.ClassTypeAndroid
import io.github.cdsap.projectgenerator.model.TypeProject
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

class Manifest {
    fun createManifest(
        moduleFolder: File,
        layer: Int,
        module: String,
        moduleType: TypeProject,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ) {
        val manifestFile = File(moduleFolder, "AndroidManifest.xml")
        manifestFile.parentFile.mkdirs()

        val manifestContent = when (moduleType) {
            TypeProject.ANDROID_APP -> {
                val s = a.filter { it.key == module }
                val name = if (s.isNotEmpty()) {
                    val x = s.values.flatten().firstOrNull { it.type == ClassTypeAndroid.ACTIVITY }
                    x?.className ?: ""
                } else {
                    ""
                }
                val composeActivityName = ".$name"
                val applicationName = ".MainApplication"
                """
                    <?xml version="1.0" encoding="utf-8"?>
                    <manifest xmlns:android="http://schemas.android.com/apk/res/android">

                        <application
                            android:allowBackup="true"
                            android:name="$applicationName"
                            android:supportsRtl="true"
                            android:theme="@style/Theme.MaterialComponents.DayNight.DarkActionBar">
                            <activity
                                android:name="$composeActivityName"
                                android:exported="true">
                                <intent-filter>
                                    <action android:name="android.intent.action.MAIN" />
                                    <category android:name="android.intent.category.LAUNCHER" />
                                </intent-filter>
                            </activity>
                        </application>
                    </manifest>
                """.trimIndent()
            }

            TypeProject.ANDROID_LIB -> """
                <?xml version="1.0" encoding="utf-8"?>
                <manifest xmlns:android="http://schemas.android.com/apk/res/android">
                </manifest>
            """.trimIndent()

            else -> ""
        }

        manifestFile.writeText(manifestContent)
    }
}
