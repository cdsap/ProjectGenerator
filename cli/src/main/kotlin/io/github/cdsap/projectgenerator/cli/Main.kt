package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import io.github.cdsap.projectgenerator.ProjectGenerator
import io.github.cdsap.projectgenerator.ProjectGeneratorVersion
import io.github.cdsap.projectgenerator.model.*
import io.github.cdsap.projectgenerator.writer.GradleWrapper
import java.io.File
import kotlin.text.buildString

fun main(args: Array<String>) {
    ProjectReportCli()
        .subcommands(GenerateProjects(), GenerateYaml())
        .main(args)
}

class ProjectReportCli : CliktCommand() {
    init {
        versionOption(ProjectGeneratorVersion.value)
    }

    override fun run() = Unit // Root doesn't do anything itself
}

class GenerateProjects : CliktCommand(name = "generate-project") {
    private val shape: String by option().choice(
        "rhombus", "triangle", "flat",
        "rectangle", "middle_bottleneck", "inverse_triangle"
    ).default("rectangle")
    private val language: String by option().choice("kts", "groovy", "both").default("kts")
    private val modules by option().int().required()
        .check("max number of projects 4000") { it in (layers + 1)..4000 }
    private val type by option().choice("android", "jvm").default("android")
    private val di: String by option().choice("hilt", "metro", "none").default("hilt")
    private val classesModule by option().int().default(10)
        .check("classes per module must be >= ${ClassesPerModule.MIN_CLASSES_PER_MODULE}") {
            it >= ClassesPerModule.MIN_CLASSES_PER_MODULE
        }
    private val classesModuleType: String by option().choice("fixed", "random").default("fixed")
    private val typeOfStringResources: String by option().choice("large", "normal").default("normal")
    private val layers by option().int().default(5)
    private val generateUnitTest by option().flag(default = false)
    private val gradle: String? by option()
        .convert {
            if (!Gradle.isSupportedValue(it)) {
                fail("Unknown Gradle version: $it. Supported versions: ${Gradle.supportedDisplayValues()}")
            }
            it
        }
    private val develocity by option().flag(default = false)
    private val versionsFile by option().file()
    private val outputDir by option("--output-dir")
    private val projectName by option()
    private val develocityUrl by option()
    // Kept for CLI backwards compatibility. AGP 9 is the default now.
    private val agp9 by option().flag(default = false)
    private val roomDatabase by option("--room-database").flag(default = false)
    private val kotlinMultiplatformLibrary by option("--android-kotlin-multiplatform-library").flag(default = false)


    override fun run() {
        val typeOfProjectRequested = TypeProjectRequested.valueOf(type.uppercase())
        val shape = Shape.valueOf(shape.uppercase())
        val dependencyInjection = DependencyInjection.valueOf(di.uppercase())
        if (typeOfProjectRequested != TypeProjectRequested.ANDROID && kotlinMultiplatformLibrary) {
            throw UsageError("--android-kotlin-multiplatform-library is only available when --type android.")
        }
        val versionsOverride = versionsFile?.let(VersionsParser::fromFile)
        val versions = getVersions(
            fileVersions = versionsOverride,
            develocityUrl = develocityUrl,
            roomDatabase = roomDatabase,
            kotlinMultiplatformLibrary = kotlinMultiplatformLibrary
        ).copy(di = dependencyInjection)
        val develocityEnabled = getDevelocityEnabled(develocity, develocityUrl)
        val language = Language.valueOf(language.uppercase())
        val resolvedProjectName = projectName ?: buildString {
            append(typeOfProjectRequested.name.lowercase())
            append(shape.name.lowercase().replaceFirstChar { it.uppercase() })
            append(modules)
            append("modules")
        }
        ProjectGenerator(
            modules,
            shape,
            language,
            typeOfProjectRequested,
            ClassesPerModule(ClassesPerModuleType.valueOf(classesModuleType.uppercase()), classesModule),
            versions = versions,
            TypeOfStringResources.valueOf(typeOfStringResources.uppercase()),
            layers,
            generateUnitTest,
            GradleWrapper(resolveGradle(gradle, versionsOverride)),
            projectRootPath = resolveProjectRootPath(outputDir, language, resolvedProjectName),
            develocity = develocityEnabled,
            projectName = resolvedProjectName
        ).write()
    }

    private fun getDevelocityEnabled(develocity: Boolean, develocityUrl: String?): Boolean {
        return if (develocity) {
            return true
        } else {
            develocityUrl != null
        }
    }

    private fun getVersions(
        fileVersions: VersionsFile?,
        develocityUrl: String?,
        roomDatabase: Boolean,
        kotlinMultiplatformLibrary: Boolean
    ): Versions {
        val versions = if (fileVersions != null) {
            fileVersions.resolve()
        } else {
            Versions()
        }
        var androidConfig = versions.android
        if (roomDatabase) {
            androidConfig = androidConfig.copy(roomDatabase = true)
        }
        if (kotlinMultiplatformLibrary) {
            androidConfig = androidConfig.copy(kotlinMultiplatformLibrary = true)
        }
        val withAndroidFlags = versions.copy(android = androidConfig)
        return if (develocityUrl != null) {
            withAndroidFlags.copy(project = withAndroidFlags.project.copy(develocityUrl = develocityUrl))
        } else {
            withAndroidFlags
        }

    }
}

internal fun resolveProjectRootPath(outputDir: String?, language: Language, projectName: String): String {
    return if (outputDir != null) {
        outputDir
    } else {
        when (language) {
            Language.KTS -> "projects_generated/$projectName/project_kts"
            Language.GROOVY -> "projects_generated/$projectName/project_groovy"
            Language.BOTH -> "projects_generated/$projectName"
        }
    }
}

internal fun resolveGradle(cliGradle: String?, versionsFile: VersionsFile?): Gradle {
    return cliGradle?.let(Gradle::fromValue)
        ?: versionsFile?.gradle
        ?: Gradle.latest()
}

class GenerateYaml : CliktCommand(name = "generate-yaml-versions") {
    override fun run() {
        GenerateVersionsYaml().generate()
    }
}
