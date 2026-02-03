package io.github.cdsap.projectgenerator.generator.classes

import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.NameMappings
import io.github.cdsap.projectgenerator.writer.ClassGenerator
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.text.appendLine

/**
 * Generates class files based on module class definitions
 */
data class GenerateDictionaryAndroid(
    val className: String,
    val type: ClassTypeAndroid,
    val index: Int,
    val dependencies: List<ClassDependencyAndroid>
)

class ClassGeneratorAndroid(
    private val di: DependencyInjection
) :
    ClassGenerator<ModuleClassDefinitionAndroid, GenerateDictionaryAndroid> {


    override fun obtainClassesGenerated(
        moduleDefinition: ModuleClassDefinitionAndroid,
        classesDictionary: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>> {
        val a = CopyOnWriteArrayList<GenerateDictionaryAndroid>()
        moduleDefinition.classes.forEach { classDefinition ->
            val className =
                "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}"
            a.add(
                GenerateDictionaryAndroid(
                    className,
                    classDefinition.type,
                    classDefinition.index,
                    classDefinition.dependencies
                )
            )
        }
        classesDictionary[moduleDefinition.moduleId] = a
        return classesDictionary

    }

    override fun generate(
        moduleDefinition: ModuleClassDefinitionAndroid,
        projectName: String,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ) {

        moduleDefinition.classes.forEach { classDefinition ->
            val classContent = generateClassContent(classDefinition, moduleDefinition, a)
            writeClassFile(classContent, classDefinition, moduleDefinition, projectName)
        }

        if (di == DependencyInjection.HILT) {
            createDaggerModule(moduleDefinition, projectName, a)
        }

    }

    private fun createDaggerModule(
        moduleDefinition: ModuleClassDefinitionAndroid,
        projectName: String,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ) {
        val packageName = "com.awesomeapp.${NameMappings.modulePackageName(moduleDefinition.moduleId)}"
        val moduleName = "Module_${moduleDefinition.moduleNumber}"
        // Create imports for this module's classes
        val classImports = StringBuilder()
        val provideMethods = mutableListOf<String>()

        moduleDefinition.classes.forEach { classDefinition ->
            if (classDefinition.type != ClassTypeAndroid.STATE) { // Skip STATE type classes
                val className =
                    "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}"
                classImports.appendLine("import $packageName.$className")

                when (classDefinition.type) {
                    ClassTypeAndroid.API -> {
                        provideMethods.add(
                            """
                        |    @Provides
                        |    @Singleton
                        |    fun provide$className(): $className {
                        |        return $className()
                        |    }
                        """.trimMargin()
                        )
                    }

                    ClassTypeAndroid.REPOSITORY -> {
                        // For repositories, we need to provide their API dependencies
                        if (classDefinition.dependencies.isNotEmpty()) {
                            val xa = mutableListOf<String>()
                            classDefinition.dependencies.mapIndexed { index, dep ->
                                val depModuleId = dep.sourceModuleId
                                val s = a.filter { it.key == depModuleId }
                                if (s.isNotEmpty()) {
                                    val x = s.values.flatten().first { it.type == ClassTypeAndroid.API }
                                    if (x != null) {

                                        val apiClassName = x.className
                                        classImports.appendLine(
                                            "import com.awesomeapp.${
                                                NameMappings.modulePackageName(
                                                    dep.sourceModuleId
                                                )
                                            }.$apiClassName"
                                        )
                                        xa.add("api$index: $apiClassName = $apiClassName()")

                                    }
                                }
                            }
                            val constructorParams = xa.joinToString(",\n        ")

                            provideMethods.add(
                                """
                            |    @Provides
                            |    @Singleton
                            |    fun provide$className(
                            |        $constructorParams
                            |    ): $className {
                            |        return $className(${
                                    constructorParams.split(",").map { it.split(":").first() }.joinToString(", ")
                                })
                            |    }
                            """.trimMargin()
                            )
                        } else {
                            provideMethods.add(
                                """
                            |    @Provides
                            |    @Singleton
                            |    fun provide$className(): $className {
                            |        return $className()
                            |    }
                            """.trimMargin()
                            )
                        }
                    }

                    else -> {
                    }

                }
            }
        }

        // Only create the module if we have something to provide
        if (provideMethods.isNotEmpty()) {
            val content = """
                |package $packageName.di
                |
                |import dagger.Module
                |import dagger.Provides
                |import dagger.hilt.InstallIn
                |import dagger.hilt.components.SingletonComponent
                |import javax.inject.Singleton
                |${classImports.toString().trim()}
                |
                |@Module
                |@InstallIn(SingletonComponent::class)
                |object $moduleName {
                |${provideMethods.joinToString("\n\n")}
                |}
            """.trimMargin()

            val layerDir = NameMappings.layerName(moduleDefinition.layer)
            val moduleDir = NameMappings.moduleName(moduleDefinition.moduleId)
            val diPackagePath =
                "${projectName}/$layerDir/$moduleDir/src/main/kotlin/${
                    packageName.replace(
                        ".",
                        "/"
                    )
                }/di"
            File(diPackagePath).mkdirs()
            File("$diPackagePath/$moduleName.kt").writeText(content)
        }
    }

    private fun generateClassContent(
        classDefinition: ClassDefinitionAndroid,
        moduleDefinition: ModuleClassDefinitionAndroid,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val packageName = "com.awesomeapp.${NameMappings.modulePackageName(moduleDefinition.moduleId)}"
        val className = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}"

        return when (classDefinition.type) {
            ClassTypeAndroid.VIEWMODEL -> generateViewModel(
                packageName,
                className,
                classDefinition.dependencies,
                a
            )

            ClassTypeAndroid.REPOSITORY -> generateRepository(packageName, className, classDefinition.dependencies, a)
            ClassTypeAndroid.API -> generateApi(packageName, className)
            ClassTypeAndroid.WORKER -> generateWorker(packageName, className)
            ClassTypeAndroid.ACTIVITY -> generateComposeActivity(packageName, className, moduleDefinition.moduleNumber)
            ClassTypeAndroid.FRAGMENT -> generateFragment(packageName, className)
            ClassTypeAndroid.SERVICE -> generateService(packageName, className)
            ClassTypeAndroid.STATE -> generateState(packageName, className)
            ClassTypeAndroid.MODEL -> generateModel(packageName, className)
            ClassTypeAndroid.USECASE -> generateUseCase(
                packageName,
                className,
                moduleDefinition.moduleNumber.toString()
            )

            else -> throw IllegalArgumentException("Unsupported class type: ${classDefinition.type}")
        }
    }

    private fun generateViewModel(
        packageName: String,
        className: String,
        dependencies: List<ClassDependencyAndroid>,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {

        val imports = buildString {
            appendLine("import androidx.lifecycle.ViewModel")
            appendLine("import androidx.lifecycle.viewModelScope")
            appendLine("import kotlinx.coroutines.launch")
            appendLine("import kotlinx.coroutines.coroutineScope")
            appendLine("import kotlinx.coroutines.async")
            appendLine("import kotlinx.coroutines.awaitAll")
            appendLine("import kotlinx.coroutines.flow.MutableStateFlow")
            appendLine("import kotlinx.coroutines.flow.StateFlow")
            appendLine("import kotlinx.coroutines.flow.asStateFlow")
            appendLine("import kotlinx.coroutines.Dispatchers")
            when (di) {
                DependencyInjection.HILT -> {
                    appendLine("import dagger.hilt.android.lifecycle.HiltViewModel")
                    appendLine("import javax.inject.Inject")
                }
                DependencyInjection.METRO -> {
                    appendLine("import dev.zacsweers.metro.Inject")
                }
                DependencyInjection.NONE -> {}
            }
            dependencies.forEach { dep ->
                val depModuleId = dep.sourceModuleId
                val s = a.filter { it.key == depModuleId }
                if (s.isNotEmpty()) {
                    val x = s.values.flatten().firstOrNull { it.type == ClassTypeAndroid.REPOSITORY }
                    if (x != null) {
                        val repoClassName = x.className
                        appendLine("import com.awesomeapp.${NameMappings.modulePackageName(dep.sourceModuleId)}.$repoClassName")
                    }
                }
            }
        }

        val constructorParams = dependencies.mapIndexed { index, dep ->
            val s = a.filter { it.key == dep.sourceModuleId }.values.flatten()
                .first { it.type == ClassTypeAndroid.REPOSITORY }
            val repoClassName = s.className
            "private val repository$index: $repoClassName"
        }.joinToString(",\n    ")

        // Step 1: Generate the data fetching logic as a clean, un-indented block using trimIndent().
        val dataAssignmentBlock = if (dependencies.isEmpty()) {
            """
        val data = "Data from $className"
        """.trimIndent()
        } else {
            val lambdaList = dependencies.indices.joinToString(",\n                    ") {
                "{ repository$it.getData() }"
            }
            """
        val data = coroutineScope {
            val fetchers = listOf<suspend () -> String>(
                $lambdaList
            )
            val results = fetchers.map { fetcher ->
                async { fetcher() }
            }.awaitAll()
            results.joinToString("")
        }
        """.trimIndent()
        }

        val diAnnotation = when (di) {
            DependencyInjection.HILT -> "@HiltViewModel"
            DependencyInjection.METRO, DependencyInjection.NONE -> ""
        }
        val injectAnnotation = when (di) {
            DependencyInjection.HILT, DependencyInjection.METRO -> "@Inject "
            DependencyInjection.NONE -> ""
        }

        return """
    |package $packageName
    |
    |$imports
    |
    |$diAnnotation
    |class $className ${injectAnnotation}constructor(
    |    $constructorParams
    |) : ViewModel() {
    |    private val _state = MutableStateFlow<String>("")
    |    val state: StateFlow<String> = _state.asStateFlow()
    |
    |    init {
    |        viewModelScope.launch(Dispatchers.IO) {
    |            try {
    |${dataAssignmentBlock.prependIndent("                ")}
    |                _state.emit(data)
    |            } catch (e: Exception) {
    |                _state.emit("Error: ${'$'}{e.message}")
    |            }
    |        }
    |    }
    |}
    """.trimMargin()
    }

    private fun generateRepository(
        packageName: String,
        className: String,
        dependencies: List<ClassDependencyAndroid>,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val imports = buildString {
            appendLine("import kotlinx.coroutines.Dispatchers")
            appendLine("import kotlinx.coroutines.withContext")
            appendLine("import kotlinx.coroutines.coroutineScope")
            appendLine("import kotlinx.coroutines.async")
            appendLine("import kotlinx.coroutines.awaitAll")
            when (di) {
                DependencyInjection.HILT -> {
                    appendLine("import javax.inject.Inject")
                    appendLine("import javax.inject.Singleton")
                }
                DependencyInjection.METRO -> {
                    appendLine("import dev.zacsweers.metro.Inject")
                }
                DependencyInjection.NONE -> {}
            }
            dependencies.forEach { dep ->
                val depModuleId = dep.sourceModuleId
                val matches = a.filter { it.key == depModuleId }
                if (matches.isNotEmpty()) {
                    val apiClass = matches.values.flatten().firstOrNull { it.type == ClassTypeAndroid.API }
                    if (apiClass != null) {
                        appendLine("import com.awesomeapp.${NameMappings.modulePackageName(depModuleId)}.${apiClass.className}")
                    }
                }
            }
        }

        val constructorParams = dependencies.mapIndexedNotNull { index, dep ->
            val depModuleId = dep.sourceModuleId
            val apiClass = a[depModuleId]?.firstOrNull { it.type == ClassTypeAndroid.API }
            apiClass?.let { "private val api$index: ${it.className}" }
        }.joinToString(",\n    ")

        val dataFetchLogic = if (dependencies.isEmpty()) {
            "\"Data from $className Repository\""
        } else {
            val lambdaList = dependencies.indices.joinToString(",\n                    ") { "{ api$it.fetchData() }" }
            """
        coroutineScope {
            val apis = listOf<suspend () -> String>(
                $lambdaList
            )
            val results = apis.map { fetcher ->
                async { fetcher() }
            }.awaitAll()
            results.joinToString("")
        }
        """.trimIndent()
        }

        val singletonAnnotation = if (di == DependencyInjection.HILT) "@Singleton" else ""
        val injectAnnotation = when (di) {
            DependencyInjection.HILT, DependencyInjection.METRO -> "@Inject "
            DependencyInjection.NONE -> ""
        }

        return """
    |package $packageName
    |
    |$imports
    |
    |$singletonAnnotation
    |class $className ${injectAnnotation}constructor(
    |    $constructorParams
    |) {
    |    suspend fun getData(): String = withContext(Dispatchers.IO) {
    |${dataFetchLogic.prependIndent("            ")}
    |    }
    |}
    """.trimMargin()
    }

    private fun generateFragment(packageName: String, className: String): String {
        val moduleId = packageName.split(".").last()
        val moduleNumber = moduleId.split("_").last()

        val imports = buildString {
            appendLine("import android.os.Bundle")
            appendLine("import android.view.LayoutInflater")
            appendLine("import android.view.View")
            appendLine("import android.view.ViewGroup")
            appendLine("import androidx.compose.foundation.layout.Box")
            appendLine("import androidx.compose.foundation.layout.fillMaxSize")
            appendLine("import androidx.compose.material3.Text")
            appendLine("import androidx.compose.runtime.Composable")
            appendLine("import androidx.compose.runtime.collectAsState")
            appendLine("import androidx.compose.runtime.getValue")
            appendLine("import androidx.compose.ui.Alignment")
            appendLine("import androidx.compose.ui.Modifier")
            appendLine("import androidx.compose.ui.platform.ComposeView")
            appendLine("import androidx.fragment.app.Fragment")
            appendLine("import androidx.fragment.app.viewModels")
            if (di == DependencyInjection.HILT) {
                appendLine("import dagger.hilt.android.AndroidEntryPoint")
            }
        }

        val entryPointAnnotation = if (di == DependencyInjection.HILT) "@AndroidEntryPoint" else ""
        val viewModelClass = "Feature${moduleNumber}_1"

        return """
            |package $packageName
            |
            |$imports
            |
            |$entryPointAnnotation
            |class $className : Fragment() {
            |
            |    override fun onCreateView(
            |        inflater: LayoutInflater,
            |        container: ViewGroup?,
            |        savedInstanceState: Bundle?
            |    ): View {
            |        return ComposeView(requireContext()).apply {
            |            setContent {
            |
            |            }
            |        }
            |    }
            |}
        """.trimMargin()
    }

    private fun generateApi(packageName: String, className: String): String {
        val injectImport = when (di) {
            DependencyInjection.HILT -> "import javax.inject.Inject"
            DependencyInjection.METRO -> "import dev.zacsweers.metro.Inject"
            DependencyInjection.NONE -> ""
        }
        val injectAnnotation = when (di) {
            DependencyInjection.HILT, DependencyInjection.METRO -> "@Inject "
            DependencyInjection.NONE -> ""
        }

        return """
            |package $packageName
            |
            |import kotlinx.coroutines.Dispatchers
            |import kotlinx.coroutines.withContext
            |$injectImport
            |
            |class $className ${injectAnnotation}constructor() {
            |    suspend fun fetchData(): String = withContext(Dispatchers.IO) {
            |        "Data from $className API"
            |    }
            |}
        """.trimMargin()
    }

    private fun generateWorker(packageName: String, className: String): String {
        if (di != DependencyInjection.HILT) {
            return """
            |package $packageName
            |
            |import android.content.Context
            |import androidx.work.CoroutineWorker
            |import androidx.work.WorkerParameters
            |
            |class $className(
            |    context: Context,
            |    params: WorkerParameters
            |) : CoroutineWorker(context, params) {
            |    override suspend fun doWork(): Result {
            |        return try {
            |            Thread.sleep(100)
            |            Result.success()
            |        } catch (e: Exception) {
            |            Result.failure()
            |        }
            |    }
            |}
        """.trimMargin()
        }
        return """
            |package $packageName
            |
            |import android.content.Context
            |import androidx.hilt.work.HiltWorker
            |import androidx.work.CoroutineWorker
            |import androidx.work.WorkerParameters
            |import dagger.assisted.Assisted
            |import dagger.assisted.AssistedInject
            |import javax.inject.Inject
            |
            |@HiltWorker
            |class $className @AssistedInject constructor(
            |    @Assisted private val context: Context,
            |    @Assisted private val params: WorkerParameters
            |) : CoroutineWorker(context, params) {
            |    override suspend fun doWork(): Result {
            |        return try {
            |            Thread.sleep(100)
            |            Result.success()
            |        } catch (e: Exception) {
            |            Result.failure()
            |        }
            |    }
            |}
        """.trimMargin()
    }

    private fun generateComposeActivity(packageName: String, className: String, moduleNumber: Int): String {
        val moduleId = packageName.split(".").last()
        val viewModelClass = "Viewmodel${moduleNumber}_1"

        val imports = buildString {
            appendLine("import android.os.Bundle")
            appendLine("import androidx.activity.ComponentActivity")
            appendLine("import androidx.activity.compose.setContent")
            appendLine("import androidx.activity.viewModels")
            appendLine("import androidx.compose.foundation.layout.Box")
            appendLine("import androidx.compose.foundation.layout.fillMaxSize")
            appendLine("import androidx.compose.material3.Text")
            appendLine("import androidx.compose.runtime.Composable")
            appendLine("import androidx.compose.runtime.collectAsState")
            appendLine("import androidx.compose.runtime.getValue")
            appendLine("import androidx.compose.ui.Alignment")
            appendLine("import androidx.compose.ui.Modifier")
            appendLine("import com.awesomeapp.${NameMappings.modulePackageName(moduleId)}.ui.theme.FeatureTheme")
            if (di == DependencyInjection.HILT) {
                appendLine("import dagger.hilt.android.AndroidEntryPoint")
            }
        }

        val entryPointAnnotation = if (di == DependencyInjection.HILT) "@AndroidEntryPoint" else ""

        return """
            |package $packageName
            |
            |$imports
            |
            |$entryPointAnnotation
            |class $className : ComponentActivity() {
            |    private val viewModel: $viewModelClass by viewModels()
            |
            |    override fun onCreate(savedInstanceState: Bundle?) {
            |        super.onCreate(savedInstanceState)
            |        setContent {
            |            FeatureTheme {
            |                FeatureScreen_${className}(viewModel)
            |            }
            |        }
            |    }
            |}
            |
            |@Composable
            |fun FeatureScreen_${className}(viewModel: $viewModelClass) {
            |    val state by viewModel.state.collectAsState()
            |
            |    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            |        Text(text = state)
            |    }
            |}
        """.trimMargin()
    }

    private fun generateService(packageName: String, className: String): String {
        val imports = buildString {
            appendLine("import android.app.Service")
            appendLine("import android.content.Intent")
            appendLine("import android.os.IBinder")
            if (di == DependencyInjection.HILT) {
                appendLine("import dagger.hilt.android.AndroidEntryPoint")
            }
            appendLine("import kotlinx.coroutines.*")
        }

        val entryPointAnnotation = if (di == DependencyInjection.HILT) "@AndroidEntryPoint" else ""

        return """
            |package $packageName
            |
            |$imports
            |
            |$entryPointAnnotation
            |class $className : Service() {
            |    private val serviceJob = Job()
            |    private val serviceScope = CoroutineScope(Dispatchers.Default + serviceJob)
            |
            |    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            |        serviceScope.launch {
            |            delay(100)
            |        }
            |        return Service.START_STICKY
            |    }
            |
            |    override fun onBind(intent: Intent?): IBinder? = null
            |
            |    override fun onDestroy() {
            |        super.onDestroy()
            |        serviceJob.cancel()
            |    }
            |}
        """.trimMargin()
    }

    private fun generateState(packageName: String, className: String): String {
        return """
            |package $packageName
            |
            |sealed class $className {
            |    data object Loading : $className()
            |    data class Success(val data: String) : $className()
            |    data class Error(val message: String) : $className()
            |
            |    companion object {
            |        fun loading() = Loading
            |        fun success(data: String) = Success(data)
            |        fun error(message: String) = Error(message)
            |    }
            |}
        """.trimMargin()
    }

    private fun generateModel(packageName: String, className: String): String {
        return """
            |package $packageName
            |
            |data class $className(
            |    val id: String = "$className-${System.currentTimeMillis()}",
            |    val name: String = "Model for $className",
            |    val description: String = "Description for $className"
            |)
        """.trimMargin()
    }

    private fun generateUseCase(packageName: String, className: String, moduleNumber: String): String {
        val injectImport = when (di) {
            DependencyInjection.HILT -> "import javax.inject.Inject"
            DependencyInjection.METRO -> "import dev.zacsweers.metro.Inject"
            DependencyInjection.NONE -> ""
        }
        val injectAnnotation = when (di) {
            DependencyInjection.HILT, DependencyInjection.METRO -> "@Inject "
            DependencyInjection.NONE -> ""
        }
        return """
            |package $packageName
            |
            |import kotlinx.coroutines.flow.Flow
            |import kotlinx.coroutines.flow.flow
            |$injectImport
            |
            |class $className ${injectAnnotation}constructor() {
            |    operator fun invoke(): Flow<String> = flow {
            |        emit("Data from $className UseCase")
            |    }
            |}
        """.trimMargin()
    }

    private fun writeClassFile(
        content: String,
        classDefinition: ClassDefinitionAndroid,
        moduleDefinition: ModuleClassDefinitionAndroid,
        projectName: String
    ) {
        val layerDir = NameMappings.layerName(moduleDefinition.layer)
        val moduleDir = NameMappings.moduleName(moduleDefinition.moduleId)
        val packageDir = NameMappings.modulePackageName(moduleDefinition.moduleId)
        val directory =
            File("$projectName/$layerDir/$moduleDir/src/main/kotlin/com/awesomeapp/$packageDir/")
        directory.mkdirs()

        val fileName = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}.kt"
        File(directory, fileName).writeText(content)
    }

    private fun createApplicationClass(moduleDefinition: ModuleClassDefinitionAndroid, projectName: String) {
        val packageName = "com.awesomeapp.${NameMappings.modulePackageName(moduleDefinition.moduleId)}"
        val content = """
            |package $packageName
            |
            |import android.app.Application
            |import androidx.hilt.work.HiltWorkerFactory
            |import androidx.work.Configuration
            |import dagger.hilt.android.HiltAndroidApp
            |import javax.inject.Inject
            |
            |@HiltAndroidApp
            |class MainApplication : Application() {
            |    @Inject
            |    lateinit var workerFactory: HiltWorkerFactory
            |
            |    override val workManagerConfiguration: Configuration
            |        get() = Configuration.Builder()
            |            .setWorkerFactory(workerFactory)
            |            .build()
            |}
        """.trimMargin()

        val layerDirApp = NameMappings.layerName(moduleDefinition.layer)
        val moduleDirApp = NameMappings.moduleName(moduleDefinition.moduleId)
        val directory = File(
            "$projectName/$layerDirApp/$moduleDirApp/src/main/kotlin/${
                packageName.replace(
                    ".",
                    "/"
                )
            }/"
        )
        directory.mkdirs()
        File(directory, "MainApplication.kt").writeText(content)
    }


}
