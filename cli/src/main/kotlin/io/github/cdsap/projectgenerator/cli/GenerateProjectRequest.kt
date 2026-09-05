package io.github.cdsap.projectgenerator.cli

import com.github.ajalt.clikt.core.UsageError
import io.github.cdsap.projectgenerator.ProjectGenerator
import io.github.cdsap.projectgenerator.model.ClassesPerModule
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
            versionsFile: VersionsFile?,
            outputDir: String?,
            projectName: String?,
            versionsOverrides: VersionsOverrides
        ): GenerateProjectRequest {
            validateAndroidOnlyFeatures(
                typeOfProjectRequested = typeOfProjectRequested,
                roomDatabase = versionsOverrides.roomDatabase,
                kotlinMultiplatformLibrary = versionsOverrides.kotlinMultiplatformLibrary
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
                versions = VersionsResolver.resolve(
                    fileVersions = versionsFile,
                    overrides = versionsOverrides
                ),
                typeOfStringResources = typeOfStringResources,
                layers = layers,
                generateUnitTest = generateUnitTest,
                gradle = VersionsResolver.resolveGradle(cliGradle, versionsFile),
                projectRootPath = resolveProjectRootPath(outputDir, language, resolvedProjectName),
                develocity = resolveDevelocityEnabled(develocityFlag, versionsOverrides.develocityUrl),
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
