package io.github.cdsap.projectgenerator.generator.android

import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.LanguageAttributes
import io.github.cdsap.projectgenerator.model.ProjectGraph
import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.generator.classes.GenerateDictionaryAndroid
import io.github.cdsap.projectgenerator.model.ClassTypeAndroid
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

class AndroidApplication {
    fun createApplicationClass(
        node: ProjectGraph,
        lang: LanguageAttributes,
        di: DependencyInjection,
        dictionary: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>,
        roomDatabase: Boolean = false
    ) {
        val layerDir = NameMappings.layerName(node.layer)
        val moduleDir = NameMappings.moduleName(node.id)
        val packageDir = NameMappings.modulePackageName(node.id)
        val appDir =
            File("${lang.projectName}/$layerDir/$moduleDir/src/main/kotlin/com/awesomeapp/$packageDir/")
        appDir.mkdirs()
        val appFile = File(appDir, "MainApplication.kt")

        val appContent = when (di) {
            DependencyInjection.HILT -> """
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

            DependencyInjection.METRO -> {
                val moduleId = NameMappings.moduleName(node.id)
                val moduleNumber = node.id.split("_").last().toInt()
                val viewModel = dictionary[moduleId]
                    ?.firstOrNull { it.type == ClassTypeAndroid.VIEWMODEL }
                    ?.className
                    ?: "Viewmodel${moduleNumber}_1"
                if (roomDatabase) {
                    val databaseClass = dictionary[moduleId]
                        ?.firstOrNull { it.type == ClassTypeAndroid.DATABASE }
                        ?.className
                        ?: "Database${moduleNumber}_1"
                    val daoClass = dictionary[moduleId]
                        ?.firstOrNull { it.type == ClassTypeAndroid.DAO }
                        ?.className
                        ?: "Dao${moduleNumber}_1"
                    """
                    |package com.awesomeapp.$packageDir
                    |
                    |import android.app.Application
                    |import android.content.Context
                    |import androidx.room.Room
                    |import dev.zacsweers.metro.DependencyGraph
                    |import dev.zacsweers.metro.Provides
                    |import dev.zacsweers.metro.createGraphFactory
                    |
                    |@DependencyGraph
                    |interface AppGraph {
                    |    val viewModel: $viewModel
                    |
                    |    @DependencyGraph.Factory
                    |    fun interface Factory {
                    |        fun create(@Provides context: Context): AppGraph
                    |    }
                    |
                    |    @Provides
                    |    fun provideDatabase(context: Context): $databaseClass {
                    |        return Room.databaseBuilder(context, $databaseClass::class.java, "$moduleId.db")
                    |            .fallbackToDestructiveMigration()
                    |            .build()
                    |    }
                    |
                    |    @Provides
                    |    fun provideDao(db: $databaseClass): $daoClass {
                    |        return db.dao()
                    |    }
                    |}
                    |
                    |class MainApplication : Application() {
                    |    val graph: AppGraph by lazy { createGraphFactory<AppGraph.Factory>().create(this) }
                    |}
                    """.trimMargin()
                } else {
                    """
                    |package com.awesomeapp.$packageDir
                    |
                    |import android.app.Application
                    |import dev.zacsweers.metro.DependencyGraph
                    |import dev.zacsweers.metro.createGraph
                    |
                    |@DependencyGraph
                    |interface AppGraph {
                    |    val viewModel: $viewModel
                    |}
                    |
                    |class MainApplication : Application() {
                    |    val graph: AppGraph by lazy { createGraph() }
                    |}
                    """.trimMargin()
                }
            }

            DependencyInjection.NONE -> """
                |package com.awesomeapp.$packageDir
                |
                |import android.app.Application
                |
                |class MainApplication : Application()
            """.trimMargin()
        }
        appFile.writeText(appContent)
    }
}
