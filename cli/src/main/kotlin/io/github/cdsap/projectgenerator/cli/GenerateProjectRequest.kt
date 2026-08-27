package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.UsageError
import io.github.cdsap.projectgenerator.ProjectGenerator
import io.github.cdsap.projectgenerator.model.ClassesPerModule
import io.github.cdsap.projectgenerator.model.DependencyInjection
import io.github.cdsap.projectgenerator.model.Gradle
import io.github.cdsap.projectgenerator.model.Language
import io.github.cdsap.projectgenerator.model.Shape
import io.github.cdsap.projectgenerator.model.TypeOfStringResources
import io.github.cdsap.projectgenerator.model.TypeProjectRequested
import io.github.cdsap.projectgenerator.model.Versions
import io.github.cdsap.projectgenerator.model.VersionsFile
import io.github.cdsap.projectgenerator.writer.GradleWrapper

data class GenerateProjectRequest(
    val modules: Int,
    val shape: Shape,
    val language: Language,
    val typeOfProjectRequested: TypeProjectRequested,
    val classesPerModule: ClassesPerModule,
    val versions: Versions,
    val typeOfStringResources: TypeOfStringResources,
    val layers: Int,
    val generateUnitTest: Boolean,
    val gradle: Gradle,
    val projectRootPath: String,
    val develocity: Boolean,
    val projectName: String
) {
    fun toProjectGenerator(): ProjectGenerator = ProjectGenerator(
        modules = modules,
        shape = shape,
        language = language,
        typeOfProjectRequested = typeOfProjectRequested,
        classesPerModule = classesPerModule,
        versions = versions,
        typeOfStringResources = typeOfStringResources,
        layers = layers,
        generateUnitTest = generateUnitTest,
        gradle = GradleWrapper(gradle),
        projectRootPath = projectRootPath,
        develocity = develocity,
        projectName = projectName
    )

    companion object {
        fun resolve(
            modules: Int,
            shape: Shape,
            language: Language,
            typeOfProjectRequested: TypeProjectRequested,
            classesPerModule: ClassesPerModule,
            typeOfStringResources: TypeOfStringResources,
            layers: Int,
            generateUnitTest: Boolean,
            cliGradle: String?,
            develocityFlag: Boolean,
            develocityUrl: String?,
            versionsFile: VersionsFile?,
            outputDir: String?,
            projectName: String?,
            dependencyInjection: DependencyInjection,
            roomDatabase: Boolean,
            kotlinMultiplatformLibrary: Boolean
        ): GenerateProjectRequest {
            validateAndroidOnlyFeatures(
                typeOfProjectRequested = typeOfProjectRequested,
                roomDatabase = roomDatabase,
                kotlinMultiplatformLibrary = kotlinMultiplatformLibrary
            )
            val resolvedProjectName = resolveProjectName(
                projectName,
                typeOfProjectRequested,
                shape,
                modules
            )
            return GenerateProjectRequest(
                modules = modules,
                shape = shape,
                language = language,
                typeOfProjectRequested = typeOfProjectRequested,
                classesPerModule = classesPerModule,
                versions = resolveVersions(
                    fileVersions = versionsFile,
                    dependencyInjection = dependencyInjection,
                    develocityUrl = develocityUrl,
                    roomDatabase = roomDatabase,
                    kotlinMultiplatformLibrary = kotlinMultiplatformLibrary
                ),
                typeOfStringResources = typeOfStringResources,
                layers = layers,
                generateUnitTest = generateUnitTest,
                gradle = resolveGradle(cliGradle, versionsFile),
                projectRootPath = resolveProjectRootPath(outputDir, language, resolvedProjectName),
                develocity = resolveDevelocityEnabled(develocityFlag, develocityUrl),
                projectName = resolvedProjectName
            )
        }
    }
}

internal fun validateAndroidOnlyFeatures(
    typeOfProjectRequested: TypeProjectRequested,
    roomDatabase: Boolean,
    kotlinMultiplatformLibrary: Boolean
) {
    if (typeOfProjectRequested != TypeProjectRequested.ANDROID && roomDatabase) {
        throw UsageError("--room-database is only available when --type android.")
    }
    if (typeOfProjectRequested != TypeProjectRequested.ANDROID && kotlinMultiplatformLibrary) {
        throw UsageError("--android-kotlin-multiplatform-library is only available when --type android.")
    }
}

internal fun resolveProjectName(
    projectName: String?,
    typeOfProjectRequested: TypeProjectRequested,
    shape: Shape,
    modules: Int
): String {
    return projectName ?: buildString {
        append(typeOfProjectRequested.name.lowercase())
        append(shape.name.lowercase().replaceFirstChar { it.uppercase() })
        append(modules)
        append("modules")
    }
}

internal fun resolveDevelocityEnabled(develocity: Boolean, develocityUrl: String?): Boolean {
    return develocity || develocityUrl != null
}

internal fun resolveVersions(
    fileVersions: VersionsFile?,
    dependencyInjection: DependencyInjection,
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
    val withAndroidFlags = versions.copy(android = androidConfig, di = dependencyInjection)
    return if (develocityUrl != null) {
        withAndroidFlags.copy(project = withAndroidFlags.project.copy(develocityUrl = develocityUrl))
    } else {
        withAndroidFlags
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
