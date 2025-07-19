package io.github.cdsap.projectgenerator.generator.classes

import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.ClassGenerator
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.text.appendLine

/**
 * Generates class files based on module class definitions
 */
data class GenerateDictionaryJvm(
    val className: String,
    val type: ClassTypeJvm,
    val index: Int
)

class ClassGeneratorJvm :
    ClassGenerator<ModuleClassDefinitionJvm, GenerateDictionaryJvm> {
    override fun obtainClassesGenerated(
        moduleDefinition: ModuleClassDefinitionJvm,
        classesDictionary: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryJvm>>
    ): MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryJvm>> {
        val a = CopyOnWriteArrayList<GenerateDictionaryJvm>()
        moduleDefinition.classes.forEach { classDefinition ->
            val className = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}"
            a.add(GenerateDictionaryJvm(className, classDefinition.type, classDefinition.index))
        }
        classesDictionary[moduleDefinition.moduleId] = a
        return classesDictionary

    }

    override fun generate(
        moduleDefinition: ModuleClassDefinitionJvm,
        projectName: String,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryJvm>>
    )  {
        moduleDefinition.classes.forEach { classDefinition ->
            val classContent = generateClassContent(classDefinition, moduleDefinition, a)
            writeClassFile(classContent, classDefinition, moduleDefinition, projectName)
        }
    }


    private fun generateClassContent(
        classDefinition: ClassDefinitionJvm,
        moduleDefinition: ModuleClassDefinitionJvm,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryJvm>>
    ): String {
        val packageName = "com.awesomeapp.${moduleDefinition.moduleId}"
        val className = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}"

        return when (classDefinition.type) {
            ClassTypeJvm.REPOSITORY -> generateRepository(packageName, className, classDefinition.dependencies, a)
            ClassTypeJvm.API -> generateApi(packageName, className)
            ClassTypeJvm.STATE -> generateState(packageName, className)
            ClassTypeJvm.MODEL -> generateModel(packageName, className)
            ClassTypeJvm.USECASE -> generateUseCase(packageName, className, moduleDefinition.moduleNumber.toString())
            else -> throw IllegalArgumentException("Unsupported class type: ${classDefinition.type}")
        }
    }

    private fun generateRepository(
        packageName: String,
        className: String,
        dependencies: List<ClassDependencyJvm>,
        a: MutableMap<String, CopyOnWriteArrayList<GenerateDictionaryJvm>>
    ): String {
        val imports = buildString {
            appendLine("import kotlinx.coroutines.Dispatchers")
            appendLine("import kotlinx.coroutines.withContext")
            dependencies.forEach { dep ->
                val depModuleId = dep.sourceModuleId
                val s = a.filter { it.key == depModuleId }
                if (s.isNotEmpty()) {
                    val x = s.values.flatten().first { it.type == ClassTypeJvm.API }
                    if (x != null) {
                        val apiClassName = x.className
                        appendLine("import com.awesome.$depModuleId.$apiClassName")
                    }
                }

            }
        }
        val xa = mutableListOf<String>()

        dependencies.mapIndexed { index, dep ->
            val depModuleId = dep.sourceModuleId
            val s = a.filter { it.key == depModuleId }
            if (s.isNotEmpty()) {
                val x = s.values.flatten().first { it.type == ClassTypeJvm.API }
                if (x != null) {
                    val apiClassName = x.className
                    xa.add("private val api$index: $apiClassName = $apiClassName()")
                    //  "private val api$index: $apiClassName = $apiClassName()"

                }
            }
        }
        val constructorParams = xa.joinToString(",\n    ")

        val dataFetchLogic = if (dependencies.isEmpty()) {
            "\"Data from $className Repository\""
        } else {
            dependencies.mapIndexed { index, _ ->

                "api$index.fetchData()"
            }.joinToString(" + ")
        }


        return """
            |package $packageName
            |
            |$imports
            |
            |class $className(
            |    $constructorParams
            |) {
            |    suspend fun getData(): String = withContext(Dispatchers.IO) {
            |        $dataFetchLogic
            |    }
            |}
        """.trimMargin()
    }

    private fun generateApi(packageName: String, className: String): String {
        return """
            |package $packageName
            |
            |import kotlinx.coroutines.Dispatchers
            |import kotlinx.coroutines.withContext
            |
            |class $className {
            |    suspend fun fetchData(): String = withContext(Dispatchers.IO) {
            |        "Data from $className API"
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
        return """
            |package $packageName
            |
            |import kotlinx.coroutines.flow.Flow
            |import kotlinx.coroutines.flow.flow
            |
            |class $className {
            |    operator fun invoke(): Flow<String> = flow {
            |        emit("Data from $className UseCase")
            |    }
            |}
        """.trimMargin()
    }

    private fun writeClassFile(
        content: String,
        classDefinition: ClassDefinitionJvm,
        moduleDefinition: ModuleClassDefinitionJvm,
        projectName: String
    ) {
        val directory =
            File("$projectName/layer_${moduleDefinition.layer}/${moduleDefinition.moduleId}/src/main/kotlin/com/awesomeapp/${moduleDefinition.moduleId}/")
        directory.mkdirs()

        val fileName = "${classDefinition.type.className()}${moduleDefinition.moduleNumber}_${classDefinition.index}.kt"
        File(directory, fileName).writeText(content)
    }



}
