package io.github.cdsap.projectgenerator.generator.android

import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.NameMappings
import java.io.File

class AndroidApplication {
    fun createApplicationClass(node: ProjectGraph, lang: LanguageAttributes) {
        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        val packageDir = NameMappings.modulePackageName(node.id)
        val appDir =
            File("${lang.projectName}/$layerDir/$moduleDir/src/main/kotlin/com/awesomeapp/$packageDir/")
        appDir.mkdirs()
        val appFile = File(appDir, "MainApplication.kt")

        // Create a more robust Application class that properly initializes Hilt
        val appContent = """
            |package com.awesomeapp.$packageDir
            |
            |import android.app.Application
            |import android.content.Context
            |import androidx.hilt.work.HiltWorkerFactory
            |import androidx.work.Configuration
            |import dagger.hilt.android.HiltAndroidApp
            |import dagger.hilt.android.qualifiers.ApplicationContext
            |import javax.inject.Inject
            |
            |/**
            | * Main application class with Hilt support
            | */
            |@HiltAndroidApp
            |class MainApplication : Application(){
            |
            |    override fun onCreate() {
            |        super.onCreate()
            |        // Application initialization here
            |    }
            |
            |    companion object {
            |        /**
            |         * Gets an entry point from the Hilt component
            |         */
            |        @JvmStatic
            |        fun getEntryPoint(@ApplicationContext context: Context, entryPoint: Class<*>): Any {
            |            return try {
            |                val entryPointAccessors = Class.forName("dagger.hilt.android.EntryPointAccessors")
            |                val getMethod = entryPointAccessors.getDeclaredMethod("fromApplication", Context::class.java, Class::class.java)
            |                getMethod.invoke(null, context.applicationContext, entryPoint)
            |            } catch (e: Exception) {
            |                throw RuntimeException("Error accessing Hilt entry point", e)
            |            }
            |        }
            |
            |        /**
            |         * Gets a specific implementation from an entry point
            |         */
            |        @JvmStatic
            |        inline fun <reified T> getImplementation(@ApplicationContext context: Context, entryPoint: Class<*>): T {
            |            val ep = getEntryPoint(context, entryPoint)
            |            // Find method that returns T
            |            val method = entryPoint.methods.find {
            |                it.returnType == T::class.java
            |            } ?: throw RuntimeException("No method found returning")
            |
            |            return method.invoke(ep) as T
            |        }
            |    }
            |}
        """.trimMargin()
        appFile.writeText(appContent)
    }
}
