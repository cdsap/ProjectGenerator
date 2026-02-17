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
        val classImports = StringBuilder()
        val provideMethods = mutableListOf<String>()

        val databaseClass = moduleDefinition.classes.firstOrNull { it.type == ClassTypeAndroid.DATABASE }
            ?.let { "${it.type.className()}${moduleDefinition.moduleNumber}_${it.index}" }
        val daoClass = moduleDefinition.classes.firstOrNull { it.type == ClassTypeAndroid.DAO }
            ?.let { "${it.type.className()}${moduleDefinition.moduleNumber}_${it.index}" }

        if (databaseClass != null) {
            classImports.appendLine("import $packageName.$databaseClass")
            provideMethods.add(
                """
                |    @Provides
                |    @Singleton
                |    fun provide$databaseClass(@ApplicationContext context: Context): $databaseClass {
                |        return Room.databaseBuilder(context, $databaseClass::class.java, "${moduleDefinition.moduleId}.db")
                |            .fallbackToDestructiveMigration()
                |            .build()
                |    }
                """.trimMargin()
            )
        }

        if (daoClass != null && databaseClass != null) {
            classImports.appendLine("import $packageName.$daoClass")
            provideMethods.add(
                """
                |    @Provides
                |    fun provide$daoClass(db: $databaseClass): $daoClass {
                |        return db.dao()
                |    }
                """.trimMargin()
            )
        }

        if (provideMethods.isNotEmpty()) {
            val content = """
                |package $packageName.di
                |
                |import android.content.Context
                |import androidx.room.Room
                |import dagger.Module
                |import dagger.Provides
                |import dagger.hilt.InstallIn
                |import dagger.hilt.android.qualifiers.ApplicationContext
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
                moduleDefinition,
                classDefinition.dependencies,
                a
            )

            ClassTypeAndroid.REPOSITORY -> generateRepository(packageName, className, classDefinition.dependencies, a)
            ClassTypeAndroid.API -> generateApi(packageName, className)
            ClassTypeAndroid.ENTITY -> generateEntity(packageName, className)
            ClassTypeAndroid.DAO -> generateDao(packageName, className, moduleDefinition, a)
            ClassTypeAndroid.DATABASE -> generateDatabase(packageName, className, moduleDefinition, a)
            ClassTypeAndroid.WORKER -> generateWorker(packageName, className)
            ClassTypeAndroid.ACTIVITY -> generateComposeActivity(packageName, className, moduleDefinition, a)
            ClassTypeAndroid.FRAGMENT -> generateFragment(packageName, className)
            ClassTypeAndroid.SERVICE -> generateService(packageName, className)
            ClassTypeAndroid.STATE -> generateState(packageName, className, moduleDefinition, a)
            ClassTypeAndroid.SCREEN -> generateScreen(packageName, className, moduleDefinition, a)
            ClassTypeAndroid.MODEL -> generateModel(packageName, className)
            ClassTypeAndroid.USECASE -> generateUseCase(
                packageName,
                className,
                moduleDefinition,
                a
            )

            else -> throw IllegalArgumentException("Unsupported class type: ${classDefinition.type}")
        }
    }

    private fun generateViewModel(
        packageName: String,
        className: String,
        moduleDefinition: ModuleClassDefinitionAndroid,
        dependencies: List<ClassDependencyAndroid>,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {

        val imports = buildString {
            appendLine("import androidx.lifecycle.ViewModel")
            appendLine("import androidx.lifecycle.viewModelScope")
            appendLine("import kotlinx.coroutines.launch")
            appendLine("import kotlinx.coroutines.flow.collectLatest")
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
        }

        val moduleId = dependencies.firstOrNull()?.sourceModuleId ?: ""
        val useCaseClass = findClassName(a, moduleId, ClassTypeAndroid.USECASE) ?: "Usecase${moduleDefinition.moduleNumber}_1"
        val stateClass = findClassName(a, moduleId, ClassTypeAndroid.STATE) ?: "State${moduleDefinition.moduleNumber}_1"
        val modelClass = findClassName(a, moduleId, ClassTypeAndroid.MODEL) ?: "Model${moduleDefinition.moduleNumber}_1"
        val constructorParams = "private val useCase: $useCaseClass"

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
    |    private val _state = MutableStateFlow($stateClass())
    |    val state: StateFlow<$stateClass> = _state.asStateFlow()
    |
    |    init {
    |        viewModelScope.launch(Dispatchers.IO) {
    |            useCase.seedIfEmpty()
    |            useCase().collectLatest { items: List<$modelClass> ->
    |                _state.emit(_state.value.copy(items = items, isLoading = false))
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
            appendLine("import kotlinx.coroutines.flow.Flow")
            appendLine("import kotlinx.coroutines.flow.map")
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
        }

        val moduleId = dependencies.firstOrNull()?.sourceModuleId ?: ""
        val daoClass = findClassName(a, moduleId, ClassTypeAndroid.DAO) ?: "Dao1_1"
        val entityClass = findClassName(a, moduleId, ClassTypeAndroid.ENTITY) ?: "Entity1_1"
        val modelClass = findClassName(a, moduleId, ClassTypeAndroid.MODEL) ?: "Model1_1"
        val constructorParams = "private val dao: $daoClass"

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
    |    fun observeItems(): Flow<List<$modelClass>> = dao.observeAll()
    |        .map { entities -> entities.map { it.toModel() } }
    |
    |    suspend fun seedIfEmpty() = withContext(Dispatchers.IO) {
    |        if (dao.count() == 0) {
    |            dao.upsertAll(
    |                listOf(
    |                    $entityClass(id = 1, title = "Welcome", updatedAt = System.currentTimeMillis()),
    |                    $entityClass(id = 2, title = "Getting started", updatedAt = System.currentTimeMillis())
    |                )
    |            )
    |        }
    |    }
    |
    |    private fun $entityClass.toModel(): $modelClass {
    |        return $modelClass(id = id, title = title)
    |    }
    |}
    """.trimMargin()
    }
    private fun generateFragment(packageName: String, className: String): String {
        val moduleId = packageName.split(".").last()
        val layoutName = "fragment_feature_${NameMappings.modulePackageName(moduleId).lowercase()}"

        val imports = buildString {
            appendLine("import android.os.Bundle")
            appendLine("import android.view.LayoutInflater")
            appendLine("import android.view.View")
            appendLine("import android.view.ViewGroup")
            appendLine("import android.widget.TextView")
            appendLine("import androidx.fragment.app.Fragment")
            appendLine("import com.awesomeapp.${NameMappings.modulePackageName(moduleId)}.R")
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
            |class $className : Fragment() {
            |
            |    override fun onCreateView(
            |        inflater: LayoutInflater,
            |        container: ViewGroup?,
            |        savedInstanceState: Bundle?
            |    ): View {
            |        return inflater.inflate(R.layout.$layoutName, container, false)
            |    }
            |
            |    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            |        super.onViewCreated(view, savedInstanceState)
            |        view.findViewById<TextView>(R.id.text_feature)?.text = "Feature $moduleId"
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

    private fun generateEntity(packageName: String, className: String): String {
        return """
            |package $packageName
            |
            |import androidx.room.Entity
            |import androidx.room.PrimaryKey
            |
            |@Entity(tableName = "items")
            |data class $className(
            |    @PrimaryKey val id: Long,
            |    val title: String,
            |    val updatedAt: Long
            |)
        """.trimMargin()
    }

    private fun generateDao(
        packageName: String,
        className: String,
        moduleDefinition: ModuleClassDefinitionAndroid,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val moduleId = NameMappings.moduleName(moduleDefinition.moduleId)
        val entityClass = findClassName(a, moduleId, ClassTypeAndroid.ENTITY) ?: "Entity${moduleDefinition.moduleNumber}_1"
        return """
            |package $packageName
            |
            |import androidx.room.Dao
            |import androidx.room.Insert
            |import androidx.room.OnConflictStrategy
            |import androidx.room.Query
            |import kotlinx.coroutines.flow.Flow
            |
            |@Dao
            |interface $className {
            |    @Query("SELECT * FROM items ORDER BY updatedAt DESC")
            |    fun observeAll(): Flow<List<$entityClass>>
            |
            |    @Query("SELECT COUNT(*) FROM items")
            |    suspend fun count(): Int
            |
            |    @Insert(onConflict = OnConflictStrategy.REPLACE)
            |    suspend fun upsertAll(items: List<$entityClass>)
            |}
        """.trimMargin()
    }

    private fun generateDatabase(
        packageName: String,
        className: String,
        moduleDefinition: ModuleClassDefinitionAndroid,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val moduleId = NameMappings.moduleName(moduleDefinition.moduleId)
        val entityClass = findClassName(a, moduleId, ClassTypeAndroid.ENTITY) ?: "Entity${moduleDefinition.moduleNumber}_1"
        val daoClass = findClassName(a, moduleId, ClassTypeAndroid.DAO) ?: "Dao${moduleDefinition.moduleNumber}_1"
        return """
            |package $packageName
            |
            |import androidx.room.Database
            |import androidx.room.RoomDatabase
            |
            |@Database(entities = [$entityClass::class], version = 1, exportSchema = false)
            |abstract class $className : RoomDatabase() {
            |    abstract fun dao(): $daoClass
            |}
        """.trimMargin()
    }

    private fun generateScreen(
        packageName: String,
        className: String,
        moduleDefinition: ModuleClassDefinitionAndroid,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val moduleId = NameMappings.moduleName(moduleDefinition.moduleId)
        val viewModelClass = findClassName(a, moduleId, ClassTypeAndroid.VIEWMODEL) ?: "Viewmodel${moduleDefinition.moduleNumber}_1"
        val modelClass = findClassName(a, moduleId, ClassTypeAndroid.MODEL) ?: "Model${moduleDefinition.moduleNumber}_1"
        return """
            |package $packageName
            |
            |import androidx.compose.foundation.layout.Box
            |import androidx.compose.foundation.layout.fillMaxSize
            |import androidx.compose.foundation.lazy.LazyColumn
            |import androidx.compose.foundation.lazy.items
            |import androidx.compose.material3.CircularProgressIndicator
            |import androidx.compose.material3.Text
            |import androidx.compose.runtime.Composable
            |import androidx.compose.runtime.collectAsState
            |import androidx.compose.runtime.getValue
            |import androidx.compose.ui.Alignment
            |import androidx.compose.ui.Modifier
            |
            |@Composable
            |fun $className(viewModel: $viewModelClass) {
            |    val state by viewModel.state.collectAsState()
            |
            |    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            |        if (state.isLoading) {
            |            CircularProgressIndicator()
            |        } else {
            |            LazyColumn {
            |                items(state.items) { item: $modelClass ->
            |                    Text(text = item.title)
            |                }
            |            }
            |        }
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

    private fun generateComposeActivity(
        packageName: String,
        className: String,
        moduleDefinition: ModuleClassDefinitionAndroid,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val moduleId = NameMappings.moduleName(moduleDefinition.moduleId)
        val viewModelClass = findClassName(a, moduleId, ClassTypeAndroid.VIEWMODEL) ?: "Viewmodel${moduleDefinition.moduleNumber}_1"
        val screenClass = findClassName(a, moduleId, ClassTypeAndroid.SCREEN) ?: "Screen${moduleDefinition.moduleNumber}_1"
        val useCaseClass = findClassName(a, moduleId, ClassTypeAndroid.USECASE) ?: "Usecase${moduleDefinition.moduleNumber}_1"
        val repositoryClass = findClassName(a, moduleId, ClassTypeAndroid.REPOSITORY) ?: "Repository${moduleDefinition.moduleNumber}_1"
        val daoClass = findClassName(a, moduleId, ClassTypeAndroid.DAO) ?: "Dao${moduleDefinition.moduleNumber}_1"
        val databaseClass = findClassName(a, moduleId, ClassTypeAndroid.DATABASE) ?: "Database${moduleDefinition.moduleNumber}_1"

        val imports = buildString {
            appendLine("import android.os.Bundle")
            appendLine("import androidx.activity.ComponentActivity")
            appendLine("import androidx.activity.compose.setContent")
            appendLine("import androidx.activity.viewModels")
            if (di == DependencyInjection.NONE) {
                appendLine("import androidx.lifecycle.ViewModel")
                appendLine("import androidx.lifecycle.ViewModelProvider")
                appendLine("import androidx.room.Room")
            }
            appendLine("import com.awesomeapp.${NameMappings.modulePackageName(moduleId)}.ui.theme.FeatureTheme")
            if (di == DependencyInjection.HILT) {
                appendLine("import dagger.hilt.android.AndroidEntryPoint")
            }
        }

        val entryPointAnnotation = if (di == DependencyInjection.HILT) "@AndroidEntryPoint" else ""
        val manualWiring = if (di == DependencyInjection.NONE) {
            """
            |    private val database: $databaseClass by lazy {
            |        Room.databaseBuilder(applicationContext, $databaseClass::class.java, "${moduleDefinition.moduleId}.db")
            |            .fallbackToDestructiveMigration()
            |            .build()
            |    }
            |    private val dao: $daoClass by lazy { database.dao() }
            |    private val repository: $repositoryClass by lazy { $repositoryClass(dao) }
            |    private val useCase: $useCaseClass by lazy { $useCaseClass(repository) }
            |
            |    private val viewModelFactory: ViewModelProvider.Factory by lazy {
            |        object : ViewModelProvider.Factory {
            |            @Suppress("UNCHECKED_CAST")
            |            override fun <T : ViewModel> create(modelClass: Class<T>): T {
            |                if (modelClass.isAssignableFrom($viewModelClass::class.java)) {
            |                    return $viewModelClass(useCase) as T
            |                }
            |                throw IllegalArgumentException("Unknown ViewModel class: ${'$'}modelClass")
            |            }
            |        }
            |    }
            |    private val viewModel: $viewModelClass by viewModels { viewModelFactory }
            """.trimMargin()
        } else {
            "    private val viewModel: $viewModelClass by viewModels()"
        }

        return """
            |package $packageName
            |
            |$imports
            |
            |$entryPointAnnotation
            |class $className : ComponentActivity() {
            |$manualWiring
            |
            |    override fun onCreate(savedInstanceState: Bundle?) {
            |        super.onCreate(savedInstanceState)
            |        setContent {
            |            FeatureTheme {
            |                $screenClass(viewModel)
            |            }
            |        }
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

    private fun generateState(
        packageName: String,
        className: String,
        moduleDefinition: ModuleClassDefinitionAndroid,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val moduleId = NameMappings.moduleName(moduleDefinition.moduleId)
        val modelClass = findClassName(a, moduleId, ClassTypeAndroid.MODEL) ?: "Model${moduleDefinition.moduleNumber}_1"
        return """
            |package $packageName
            |
            |data class $className(
            |    val items: List<$modelClass> = emptyList(),
            |    val isLoading: Boolean = true,
            |    val error: String? = null
            |)
        """.trimMargin()
    }
    private fun generateModel(packageName: String, className: String): String {
        return """
            |package $packageName
            |
            |data class $className(
            |    val id: Long,
            |    val title: String
            |)
        """.trimMargin()
    }

    private fun generateUseCase(
        packageName: String,
        className: String,
        moduleDefinition: ModuleClassDefinitionAndroid,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>
    ): String {
        val injectImport = when (di) {
            DependencyInjection.HILT -> "import javax.inject.Inject"
            DependencyInjection.METRO -> "import dev.zacsweers.metro.Inject"
            DependencyInjection.NONE -> ""
        }
        val injectAnnotation = when (di) {
            DependencyInjection.HILT, DependencyInjection.METRO -> "@Inject "
            DependencyInjection.NONE -> ""
        }
        val moduleId = NameMappings.moduleName(moduleDefinition.moduleId)
        val repositoryClass = findClassName(a, moduleId, ClassTypeAndroid.REPOSITORY) ?: "Repository${moduleDefinition.moduleNumber}_1"
        val modelClass = findClassName(a, moduleId, ClassTypeAndroid.MODEL) ?: "Model${moduleDefinition.moduleNumber}_1"

        return """
            |package $packageName
            |
            |import kotlinx.coroutines.flow.Flow
            |$injectImport
            |
            |class $className ${injectAnnotation}constructor(
            |    private val repository: $repositoryClass
            |) {
            |    operator fun invoke(): Flow<List<$modelClass>> = repository.observeItems()
            |
            |    suspend fun seedIfEmpty() = repository.seedIfEmpty()
            |}
        """.trimMargin()
    }

    private fun findClassName(
        classesDictionary: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryAndroid>>,
        moduleId: String,
        type: ClassTypeAndroid
    ): String? {
        if (moduleId.isBlank()) return null
        return classesDictionary[moduleId]?.firstOrNull { it.type == type }?.className
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
