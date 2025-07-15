package io.github.cdsap.projectgenerator.generator.rootproject

class Gitignore {
    fun get() =
        """
           |
           |*.apk
           |*.ap_
           |
           |*.dex
           |
           |*.class
           |
           |bin/
           |gen/
           |out/
           |build/
           |generated/
           |
           |local.properties
           |
           |.classpath
           |.project
           |
           |# Windows thumbnail db
           |.DS_Store
           |
           |*.iml
           |.idea/*
           |!.idea/copyright
           |# Keep the code styles.
           |!/.idea/codeStyles
           |/.idea/codeStyles/*
           |!/.idea/codeStyles/Project.xml
           |!/.idea/codeStyles/codeStyleConfig.xml
           |
           |.gradle
           |
           |# Android Studio captures folder
           |captures/
           |.kotlin
        """.trimMargin()
}
