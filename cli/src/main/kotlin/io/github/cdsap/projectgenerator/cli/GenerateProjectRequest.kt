package io.github.cdsap.projectgenerator.cli

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

data class GenerateProjectOptions(
    val modules: Int,
    val shape: Shape,
    val language: Language,
    val typeOfProjectRequested: TypeProjectRequested,
    val classesPerModule: ClassesPerModule,
    val typeOfStringResources: TypeOfStringResources,
    val layers: Int,
    val generateUnitTest: Boolean,
    val cliGradle: String?,
    val develocityFlag: Boolean,
    val versionsFile: VersionsFile?,
    val outputDir: String?,
    val projectName: String?,
    val versionsOverrides: VersionsOverrides
)

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
        fun resolve(options: GenerateProjectOptions): GenerateProjectRequest {
            validateAndroidOnlyFeatures(
                typeOfProjectRequested = options.typeOfProjectRequested,
                roomDatabase = options.versionsOverrides.roomDatabase,
                kotlinMultiplatformLibrary = options.versionsOverrides.kotlinMultiplatformLibrary
            )
            val resolvedProjectName = resolveProjectName(
                options.projectName,
                options.typeOfProjectRequested,
                options.shape,
                options.modules
            )
            return GenerateProjectRequest(
                modules = options.modules,
                shape = options.shape,
                language = options.language,
                typeOfProjectRequested = options.typeOfProjectRequested,
                classesPerModule = options.classesPerModule,
                versions = VersionsResolver.resolve(
                    fileVersions = options.versionsFile,
                    overrides = options.versionsOverrides
                ),
                typeOfStringResources = options.typeOfStringResources,
                layers = options.layers,
                generateUnitTest = options.generateUnitTest,
                gradle = VersionsResolver.resolveGradle(options.cliGradle, options.versionsFile),
                projectRootPath = ProjectOutputPathResolver.defaultRootPath(
                    options.outputDir,
                    options.language,
                    resolvedProjectName
                ),
                develocity = resolveDevelocityEnabled(
                    options.develocityFlag,
                    options.versionsOverrides.develocityUrl
                ),
                projectName = resolvedProjectName
            )
        }
    }
}

internal class GenerateProjectRequestValidationException(
    message: String
) : RuntimeException(message)

internal fun validateAndroidOnlyFeatures(
    typeOfProjectRequested: TypeProjectRequested,
    roomDatabase: Boolean,
    kotlinMultiplatformLibrary: Boolean
) {
    if (typeOfProjectRequested != TypeProjectRequested.ANDROID && roomDatabase) {
        throw GenerateProjectRequestValidationException("--room-database is only available when --type android.")
    }
    if (typeOfProjectRequested != TypeProjectRequested.ANDROID && kotlinMultiplatformLibrary) {
        throw GenerateProjectRequestValidationException(
            "--android-kotlin-multiplatform-library is only available when --type android."
        )
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
