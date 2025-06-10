package io.github.cdsap.projectgenerator.files.android

class AndroidManifestApp {
    fun get() = """
        |<?xml version="1.0" encoding="utf-8"?>
        |<manifest xmlns:android="http://schemas.android.com/apk/res/android"
        |    xmlns:tools="http://schemas.android.com/tools">
        |
        |    <application
        |        android:allowBackup="true"
        |        android:dataExtractionRules="@xml/data_extraction_rules"
        |        android:fullBackupContent="@xml/backup_rules"
        |        android:label="@string/app_name"
        |        android:supportsRtl="true"
        |        android:theme="@style/Theme.MyApplication"
        |        tools:targetApi="31" />
        |
        |</manifest>
    """.trimMargin()
}
